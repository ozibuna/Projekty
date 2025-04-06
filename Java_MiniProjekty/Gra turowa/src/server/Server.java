package server;

import client.game.components.Castle;
import client.game.components.map.WorldMap;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private final ServerSocket server;
    public static final int PORT = 45678;
    private int players = 0;
    private WorldMap currentMap;
    private boolean gameStarted = false;
    private List<ConnectedClient> connectedClients;
    private int playerOnTurn;
    private int playersAlive;
    private boolean isGameEnded = false;

    public static void main() {
        new Server();
    }

    public Server() {
        connectedClients = new ArrayList<>();
        playerOnTurn = 0;
        try {
            server = new ServerSocket(PORT);
            System.out.println("IP: " + InetAddress.getLocalHost().getHostAddress() + " PORT: " + server.getLocalPort());
            while (true) {
                initConnection();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initConnection() {
        Socket clientSocket;
        if (players < 4) {
            try {
                clientSocket = server.accept();
                if (clientSocket.isConnected()) {
                    new Thread(() -> {
                        System.out.println("Client " + players + ": connected");
                        ConnectedClient connectedClient = new ConnectedClient(clientSocket, players, this);
                        players++;
                        System.out.println();
                        System.out.println();
                        System.out.println("SERVER PLAYERS: " + players);
                        System.out.println();
                        System.out.println();
                        connectedClients.add(connectedClient);
                        connectedClient.readMessages();
                        connectedClient.close();
                    }).start();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void nextTurn(){
        int playerThatWasOnTurn = playerOnTurn;
        if(playersAlive == 1){
            isGameEnded = true;
            System.out.println("SERVER GAME ENDED");
        }

        do {
            playerOnTurn = (playerOnTurn + 1) % players;
        } while (!connectedClients.get(playerOnTurn).isAlive());

        for (ConnectedClient client : connectedClients){
            client.setMapReady(true);
            if(client.getPlayerNumber() == playerThatWasOnTurn){
                client.setMapReady(false);
            }
        }
    }

    public synchronized void calculatePlayerLose(){
        System.out.println("Calculating player lose");
        int i=0;
        for (Castle castle : currentMap.getCastles()){
            //System.out.println("SERVER: Castle destroyed: " + castle.isDestroyed());
            System.out.println();
            System.out.println("SERVER: Castle : " + castle.getOwnerID());
            System.out.println();
            if(castle.isDestroyed()){
                if (connectedClients.get(i).isAlive()){
                    connectedClients.get(i).setAlive(false);
                    playersAlive--;
                }

            }
            i++;
        }
        System.out.println();
        System.out.println();
        System.out.println("SERWER PLAYERS ALIVE: " + playersAlive);
        System.out.println();
        System.out.println();
    }


    public synchronized int getPlayerOnTurn(){
        return playerOnTurn;
    }

    public synchronized WorldMap getCurrentMap() {
        return currentMap;
    }

    public synchronized void setCurrentMap(WorldMap currentMap) {
        this.currentMap = currentMap;
    }

    public synchronized int getPlayers(){
        return players;
    }

    public synchronized boolean isGameStarted() {
        return gameStarted;
    }

    public synchronized void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public int getPlayersAlive() {
        return playersAlive;
    }

    public void setPlayersAlive(int playersAlive) {

        this.playersAlive = playersAlive;
    }

    public boolean isGameEnded() {
        return isGameEnded;
    }
}
