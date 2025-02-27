package server;

import client.game.components.Castle;
import client.game.components.map.WorldMap;

import java.io.*;
import java.net.Socket;

public class ConnectedClient {
    private final Socket clientSocket;
    private final DataInputStream msgIn;
    private final DataOutputStream msgOut;
    private final ObjectInputStream inStream;
    private final ObjectOutputStream outStream;
    private final int id;
    private final Server server;
    private boolean isMapReady = true;
    private boolean isAlive = true;

    public ConnectedClient(Socket clientSocket, int id, Server server) {
        this.server = server;
        this.clientSocket = clientSocket;
        this.id = id;
        try {
            System.out.println("Client " + id + ": connected");
            //this.clientSocket.setSendBufferSize(1024 * 100_000);
            //this.clientSocket.setReceiveBufferSize(1024 * 100_000);
            //this.clientSocket.setSoTimeout(10000);
            msgIn = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
            msgOut = new DataOutputStream(clientSocket.getOutputStream());
            outStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inStream = new ObjectInputStream(clientSocket.getInputStream());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
        COMMUNICATION LOGIC
        1. Players including host are connecting to the server
        2. host starts game whenever he wants, he can check how many players are connected
        3. players (not host) are requesting for info if game is started
        4. host creates map and sends it to the server and set game to started
        5. when game is finally started players request for map

        6. every client including host request for map from server all the time (e.g. every 1 second)

        7. when player is on turn he is not requesting map from server,
           he is sending his move to the server when his move is done

        8. when host is sure game is over he sends message to the server to stop the game

        !! IMPORTANT !!
        Is sending map enough to make game work?

     */

    public synchronized void readMessages() {
        String line;
        boolean read = true;
        while (read) {
            try {
                line = msgIn.readUTF();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println("Client " + id + ": " + line);
            switch (line) {
                case "DISCONNECT":
                    read = false;
                    close();
                    System.out.printf("Client %d: disconnected\n", id);
                    break;
                case "MAP_TO_CLIENT":
                    mapToClient();
                    break;
                case "START":
                    startGame();
                    break;
                case "GAME_STOP":
                    stopGame();
                    break;
                case "MAP_TO_SERVER":
                    mapToServer();
                    break;
                case "IS_GAME_STARTED":
                    isGameStarted();
                    break;
                case "GET_NUM_PLAYERS":
                    getPlayers();
                    break;
                case "ASK_IF_CAN_MOVE":
                    sendIfCanMove();
                    break;
                case "IS_MAP_READY":
                    sendMapReady();
                    break;
                case "HOW_MANY_PLAYERS":
                    sendNumberOfPlayers();
                    break;
                case "GET_ID":
                    sendID();
                    break;
                case "ASK_IF_GAME_ENDED":
                    sendIsGameEnded();
                    break;
                default:
                    System.out.println(STR."Unknown command: \{line}");
            }
        }
    }

    private void sendIsGameEnded() {
        try {
            msgOut.writeBoolean(server.isGameEnded());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendID() {
        try {
            msgOut.writeInt(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendNumberOfPlayers() {
        try {
            msgOut.writeInt(server.getPlayers());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMapReady() {
        try {
            msgOut.writeBoolean(isMapReady);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendIfCanMove() {
        try {
            msgOut.writeBoolean(server.getPlayerOnTurn() == id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized void getPlayers() {
        int players = server.getPlayers();
        try {
            msgOut.writeInt(players);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized void stopGame() {
        //todo implement game stop on server
    }

    private synchronized void startGame() {
        System.out.println("Game started");
        try {
            WorldMap worldMap = (WorldMap) inStream.readObject();
            System.out.println();
            System.out.println();
            for (Castle castle : worldMap.getCastles()) {
                System.out.println("SERVER: Castle destroyed: " + castle.isDestroyed());
            }
            System.out.println();
            System.out.println();
            server.setCurrentMap(worldMap);
            System.out.println("Map received from client");
            System.out.println(worldMap);
            server.setGameStarted(true);
            server.setPlayersAlive(server.getPlayers());
            isMapReady = false;
        } catch (Exception e) {
            System.out.println("Could not read map from client");
        }
    }

    private synchronized void isGameStarted() {
        try {
            msgOut.writeBoolean(server.isGameStarted());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized void mapToServer() {
        try {
            WorldMap worldMap = (WorldMap) inStream.readObject();
            System.out.println();
            System.out.println();
            for (Castle castle : worldMap.getCastles()) {
                System.out.println("SERVER: Castle destroyed: " + castle.isDestroyed());
            }
            System.out.println();
            System.out.println();
            server.setCurrentMap(worldMap);
            System.out.println("Map received from client");
            System.out.println(worldMap);
            server.calculatePlayerLose();
            server.nextTurn();

        } catch (Exception e) {
            System.out.println("Could not read map from client");
        }
    }


    private synchronized void mapToClient() {
        if (isMapReady) {
            try {
                outStream.reset();
                outStream.writeObject(server.getCurrentMap());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        isMapReady = false;
    }

    public boolean isMapReady() {
        return isMapReady;
    }

    public void setMapReady(boolean mapReady) {
        isMapReady = mapReady;
    }

    public int getPlayerNumber() {
        return id;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public synchronized void close() {
        try {
            msgIn.close();
            msgOut.close();
            inStream.close();
            outStream.close();
            clientSocket.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void reserializeWorldMap(WorldMap worldMap) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            out.writeObject(worldMap);
            out.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }


}
