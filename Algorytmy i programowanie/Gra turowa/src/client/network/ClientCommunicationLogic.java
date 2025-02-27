package client.network;

import client.game.management.IGameManager;
import client.game.components.map.WorldMap;
import client.game.management.Initializer;

import java.io.*;
import java.net.Socket;

public class ClientCommunicationLogic implements Runnable {
    /**
     * The `Socket` variable represents a two-way communication endpoint that allows communication between the client and the server over a network.
     * It is a private variable of the class `ClientCommunicationLogic`.
     *
     * Socket is used for establishing a connection with the server, sending and receiving data.
     *
     * Please refer to the documentation of the containing class for more information on how this variable is used in the context of the application.
     *
     * @see ClientCommunicationLogic
     */
    private Socket socket;
    /**
     *
     * The msgIn variable is a private DataInputStream for reading data from a source. It is used in the ClientCommunicationLogic class.
     *
     * Example usage:
     * ```java
     * private DataInputStream msgIn = new DataInputStream(inputStream);
     * ```
     *
     * @see ClientCommunicationLogic
     */
    private DataInputStream msgIn;
    /**
     *
     */
    private DataOutputStream msgOut;
    /**
     * Represents an input stream used for reading objects of type Object from an underlying stream.
     */
    private ObjectInputStream inStream;
    /**
     * This private variable represents the output stream used for sending objects.
     * <p>
     * The ObjectOutputStream class is used to write objects to an OutputStream,
     * which can be used to send the objects over a network or save them to a file.
     * Objects that are written using ObjectOutputStream can be read using ObjectInputStream.
     * <p>
     * This variable is typically used in the context of the ClientCommunicationLogic class.
     * It is used to send objects such as WorldMap to the server or other clients in a game.
     *
     * @see ClientCommunicationLogic
     * @see ObjectOutputStream
     * @see WorldMap
     */
    private ObjectOutputStream outStream;
    /**
     * This variable represents whether the current instance is the host or not.
     * It is a private final boolean variable.
     *
     * Use this variable to determine the role of the current instance in the application.
     * If isHost is true, the current instance is the host.
     * If isHost is false, the current instance is not the host.
     */
    private final boolean isHost;
    /**
     *
     */
    private final IGameManager manager;
    /**
     * The PORT variable represents the port number used for communication in the ClientCommunicationLogic class.
     * It is a constant value and cannot be modified once initialized.
     * The default port number is 12341.
     */
    public static final int PORT = 45678;
    /**
     * Represents the state of the host in the ClientCommunicationLogic class.
     * <p>
     * The hostState variable is a private member variable of the ClientCommunicationLogic class
     * that holds the current state of the host. It is a string that can have one of the following values:
     * <ul>
     *     <li>"BEFORE_START": Represents the state of the host before the game has started.</li>
     *     <li>"IN_GAME": Represents the state of the host during the game.</li>
     * </ul>
     * The hostState variable is used to determine the behavior of the host in different parts of the game logic.
     *
     * @see ClientCommunicationLogic
     */
    private String hostState = "BEFORE_START";
    /**
     *
     */
    private String clientState = "";

    /**
     * Constructor for ClientCommunicationLogic class.
     *
     * @param isHost  {@code true} if the current user is the host, {@code false} otherwise
     * @param manager An instance of IGameManager interface that manages the game session
     */
    public ClientCommunicationLogic(boolean isHost, IGameManager manager) {
        this.isHost = isHost;
        this.manager = manager;
    }
    /**
     * Establishes a connection with the specified IP address.
     *
     * @param ip the IP address of the server to connect to
     * @throws IOException if an I/O error occurs while connecting to the server
     */
    public void connect(String ip) throws IOException {
        Thread waitForServer = new Thread(() -> {
            while (socket == null) {
                try {
                    socket = new Socket(ip, PORT);
                } catch (IOException e) {
                    System.out.println("Server not found");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            try {
                outStream = new ObjectOutputStream(socket.getOutputStream());
                outStream.flush();
                msgOut = new DataOutputStream(socket.getOutputStream());
                inStream = new ObjectInputStream(socket.getInputStream());
                msgIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                System.out.println("Connected to server");
                msgOut.writeUTF("GET_ID");
                int id = msgIn.readInt();
                System.out.println("Player ID: " + id);
                manager.setPlayerID(id);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        waitForServer.start();
        while (waitForServer.isAlive()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        new Thread(this).start();
    }

    /**
     * Sends the updated world map to the server and sets the client state to "MOVE_DONE".
     *
     *
     */
    public void sendMove() {
        setClientState("MOVE_DONE");
    }

    /**
     * Runs the game logic in a separate thread.
     *
     * The run method contains a while loop that continuously executes the game logic.
     * It switches between different states to determine the current behavior of the game.
     *
     * <p>
     * The different states are:
     * - "BEFORE_START": Executed only by the host. It updates the map, starts threads to ask for the number of players
     * and wait for the game to start. When the game starts, it sends the start signal and the map to all clients.
     * - "IN_GAME": Executed by all players. It checks if the player can move and waits for the player to make a move.
     * - "GAME_BEING_PLAYED": Executed by all players. Waits for other players to make their move.
     *
     * The run method sleeps for a certain amount of time in each state to avoid excessive CPU usage.
     *
     * If the run method catches an InterruptedException, it re-interrupts the thread and returns.
     * </p>
     *
     */
    @Override
    public void run() {
        String state = "BEFORE_START";
        boolean gameOn = true;
        while (gameOn) {
            switch (state) {
                case "BEFORE_START":
                    state = beforeStart();
                    break;
                case "IN_GAME":
                    state = inGame();
                    break;
                case "GAME_ENDED":
                    endingGame();
                    gameOn = false;
                    break;
                default:
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }
        }
    }



    /**
     * Starts the game by setting the host state to "START" and calling the startGame method of the
     * IGameManager instance.
     *
     * @see ClientCommunicationLogic#setHostState(String)
     * @see IGameManager#startGame()
     */
    public void startGame() {
        setHostState("START");
        manager.startGame();
    }


    /**
     * This method is called before starting the game. It performs different actions based on whether the client is the host or not.
     *
     * If the client is the host, it updates the map with a new world map of size 200. Then it starts two threads:
     *  - askForPlayers: This thread continuously sends a message to the server to get the number of players connected. The received value is printed to the console.
     *      This thread runs until interrupted.
     *  - gameStartThread: This thread continuously checks the current state of the host. If the state is "START", it sets the refreshMap flag of the manager
     *      to true, interrupts the askForPlayers thread, sends a "START" message to the server, sends the current world map to the server, and breaks the loop.
     *      This thread runs until the game is started.
     *
     * If the client is not the host, it starts one thread:
     *  - startingGame: This thread continuously sends a message to the server to check if the game has started. If the game has started, it sets the refreshMap flag
     *      of the manager to true, sends a "MAP_TO_CLIENT" message to the server to get the map, updates the map with the received map, sends a "HOW_MANY_PLAYERS"
     *      message to the server to get the total number of players, and breaks the loop. Otherwise, it prints "Game not started yet" to the console.
     *      This thread runs until the game is started.
     *
     * Finally, it sets the nextState variable to "IN_GAME" and returns it.
     *
     * @return The next state of the client.
     */
    private String beforeStart() {
        String nextState;
        if (isHost) {
            manager.updateMap(new WorldMap(Initializer.MAP_SIZE, manager.getTotalPlayers()));

            Thread askForPlayers = new Thread(() -> {
                while (true) {
                    try {
                        msgOut.writeUTF("GET_NUM_PLAYERS");
                        int totalPlayers = msgIn.readInt();
                        manager.setTotalPlayers(totalPlayers);
                        System.out.println(STR."Players connected: \{totalPlayers}");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
             //  manager.updateMap(new WorldMap(Initializer.MAP_SIZE, manager.getTotalPlayers()));
            });
            Thread gameStartThread = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    String state = getHostState();
                    if (state.equals("START")) {
                        manager.setRefreshMap(true);
                        System.out.println("State spotted");
                        askForPlayers.interrupt();
                        try {
                            msgOut.writeUTF("START");
                            outStream.reset();
                            outStream.writeObject(manager.getWorldMap());
                            //generateGUI();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("Game started");
                        break;
                    }
                }
            });

            askForPlayers.start();
            gameStartThread.start();

            while (askForPlayers.isAlive() || gameStartThread.isAlive()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }


        } else {

            Thread startingGame = new Thread(() -> {
                while (true) {
                    try {
                        msgOut.writeUTF("IS_GAME_STARTED");
                        boolean isStarted = msgIn.readBoolean();
                        if (isStarted) {
                            manager.startGame();
                            System.out.println("Game started");
                            System.out.println("Getting map from server");
                            try {
                                msgOut.writeUTF("GET_NUM_PLAYERS");
                                int totalPlayers = msgIn.readInt();
                                manager.setTotalPlayers(totalPlayers);
                                System.out.println(STR."Players connected: \{totalPlayers}");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            msgOut.writeUTF("MAP_TO_CLIENT");
                            try {
                                manager.updateMap((WorldMap) inStream.readObject());
                            } catch (ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                            manager.setRefreshMap(true);
                            msgOut.writeUTF("HOW_MANY_PLAYERS");
                            manager.setTotalPlayers(msgIn.readInt());
                            break;
                        } else {
                            System.out.println("Game not started yet");
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            });

            startingGame.start();
            while (startingGame.isAlive()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }
        nextState = "IN_GAME";
        return nextState;
    }


    /**
     * Method to handle the game logic when the player is in the game.
     *
     * @return The next state after the game is finished.
     */
    private String inGame() {
        String nextState;
        boolean isGameEnded;
        try {
            msgOut.writeUTF("ASK_IF_GAME_ENDED");
            System.out.println("Asking if game ended");
            isGameEnded = msgIn.readBoolean();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(isGameEnded){
            System.out.println("Game ended");
            return "GAME_ENDED";
        }



        boolean canMove;
        try {
            msgOut.writeUTF("ASK_IF_CAN_MOVE");
            canMove = msgIn.readBoolean();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (canMove) {
            System.out.println("You can move" + manager.getPlayer().getPlayerID());
            Thread waitForSendingMove = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    boolean canMoveNow = manager.getCanMove();
                    if (canMoveNow) {
                        System.out.println("State spotted");
                        try {
                            msgOut.writeUTF("MAP_TO_SERVER");
                            outStream.reset();
                            outStream.writeObject(manager.getWorldMap());
                            //generateGUI();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("Move sent");
                        setClientState("");
                        break;
                    }
                }
            });
            waitForSendingMove.start();
            while (waitForSendingMove.isAlive()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            nextState = "IN_GAME";

        } else {
            System.out.println("You can't move");
            Thread waitForMapChange = new Thread(() -> {
                while (true) {
                    try {
                        msgOut.writeUTF("IS_MAP_READY");
                        boolean isMapReady = msgIn.readBoolean();

                        if (isMapReady) {
                            System.out.println("Move done by other player");
                            System.out.println("Getting map from server");
                            msgOut.writeUTF("MAP_TO_CLIENT");
                            try {
                                manager.updateMap((WorldMap) inStream.readObject());
                                manager.nextTurn();
                            } catch (ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                            manager.setRefreshMap(true);
                            break;
                        } else {
                            System.out.println("Move not done by other player yet");
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            });
            waitForMapChange.start();

            while (waitForMapChange.isAlive()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                msgOut.writeUTF("ASK_IF_GAME_ENDED");
                System.out.println("Asking if game ended");
                isGameEnded = msgIn.readBoolean();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(isGameEnded){
                System.out.println("Game ended");
                return "GAME_ENDED";
            }
            nextState = "IN_GAME";
            clientState = "";

        }
        return nextState;
    }


    private void endingGame() {
        close();
    }

    private void close() {
        try {
            msgOut.writeUTF("DISCONNECT");
            msgIn.close();
            msgOut.close();
            inStream.close();
            outStream.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the current state of the host.
     *
     * @return The current host state.
     */
    public synchronized String getHostState() {
        return hostState;
    }

    /**
     * Sets the host state for the client communication logic.
     * The host state represents the current state of the host.
     *
     * @param hostState the new host state to set
     */
    public synchronized void setHostState(String hostState) {
        this.hostState = hostState;
    }

    /**
     * Gets the current state of the client.
     *
     * @return The client state.
     */
    public synchronized String getClientState() {
        return clientState;
    }

    /**
     * Sets the state of the client.
     *
     * @param clientState the new state of the client
     */
    public synchronized void setClientState(String clientState) {
        this.clientState = clientState;
    }

    private void reserializeWorldMap(WorldMap worldMap) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(worldMap);
            out.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
