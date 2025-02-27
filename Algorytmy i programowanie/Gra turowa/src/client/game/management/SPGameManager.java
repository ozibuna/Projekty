package client.game.management;

import client.game.components.Castle;
import client.game.components.Player;
import client.game.components.map.WorldMap;
import client.game.components.unit.hero.*;
import client.game.components.utils.GameUtils;

import java.util.Scanner;


public class SPGameManager implements IGameManager {
    /**
     * Represents the Manager singleton instance.
     * This variable holds a reference to the sole instance of the Manager class.
     * Only one instance of Manager can exist at a time.
     */
    private static Manager instance;
    /**
     * This variable represents the status of the game (active or not).
     * <p>
     * The game is active when it is currently being played, and inactive when it has ended or has not started yet.
     *
     */
    private Player[] players;

    private boolean isGameActive;

    private boolean canMove;

    private boolean isNetworkGame;

    /**
     * The `worldMap` variable represents the world map of the game. It contains information about the tiles, castles, and NPCs of the game.
     * <p>
     * The map consists of a 2D array of tiles (`Tile[][] tiles`), a list of castles (`List<Castle> castles`), and a list of NPCs (`List<NPC> npcs`).
     * The size of the map is determined by the `size` variable.
     * <p>
     * The `WorldMap` class provides various methods to interact with the map, such as retrieving and modifying tiles, generating the map, placing units on the map, and running NPC
     * actions.
     *
     * @see WorldMap#getTiles()
     * @see WorldMap#getCastles()
     * @see WorldMap#getSize()
     * @see WorldMap#getNeighboringTiles(int, int)
     * @see WorldMap#getTile(int, int)
     * @see WorldMap#runNPCActions(Castle)
     */
    private WorldMap worldMap;
    /**
     * The currentPlayerTurn variable represents the current turn of the game.
     */
    private int currentPlayerTurn;
    /**
     * The total number of players in the game.
     * <p>
     * This variable represents the total number of players participating in the game session.
     * It is used to keep track of the current turn and determine the next player's turn.
     *
     */
    private int totalPlayers;
    /**
     * This variable represents a player in the game.
     * <p>
     * The Player class contains information about the player, such as their castle, unique identifier, and name.
     *
     * @see Player#getCastle()
     * @see Player#setCastle(Castle)
     * @see Player#getPlayerID()
     * @see Player#setPlayerID(int)
     * @see Player#getPlayerName()
     * @see Player#setPlayerName(String)
     */
    /**
     * This variable represents whether the map needs to be refreshed or not.
     * <p>
     * If refreshMap is set to true, it indicates that the map needs to be refreshed.
     * If refreshMap is set to false, it indicates that the map does not need to be refreshed.
     *
     * @see Manager#isRefreshMap()
     * @see Manager#setRefreshMap(boolean)
     */
    private boolean refreshMap;

    /**
     * Retrieves the world map.
     *
     * @return The world map.
     */
    public WorldMap getWorldMap() {
        return worldMap;
    }

    /**
     * Creates a Manager object with the specified total number of players.
     *
     * @param totalPlayers the total number of players in the game
     */
    public SPGameManager(int totalPlayers) {
        this.worldMap = new WorldMap(Initializer.MAP_SIZE, totalPlayers);
        this.isGameActive = false;
        this.totalPlayers = totalPlayers;
        this.currentPlayerTurn = 0;
        this.players = new Player[]{new Player(), new Player(), new Player(), new Player()};
    }

    public SPGameManager(IGameManager manager){
        this.worldMap=manager.getWorldMap();
        this.isGameActive = false;
        this.totalPlayers=manager.getTotalPlayers();
        this.currentPlayerTurn=0;
        this.players = new Player[]{manager.getPlayer(), new Player(), new Player(), new Player()};
        setPlayersID();
    }

    /**
     * Gets the instance of the Manager class.
     *
     * @param totalPlayers the total number of players
     * @return the instance of the Manager class
     */
    public static Manager getInstance(int totalPlayers) {
        if (instance == null) {
            instance = new Manager(totalPlayers);
        }
        return instance;
    }


    /**
     * Sets the total number of players in the game.
     *
     * @param players the total number of players
     */
    public void setTotalPlayers(int players) {
        this.totalPlayers = players;
    }

    @Override
    public void setNetworkGame(boolean networkGame) {
        isNetworkGame = networkGame;
    }

    @Override
    public boolean isNetworkGame() {
        return isNetworkGame;
    }

    @Override
    public boolean getCanMove() {
        boolean temp = canMove;
        canMove = false;
        return temp;
    }

    /**
     * Starts a new game session. If there is already an active session, it prints a message indicating that a game is already in progress.
     * Otherwise, it initializes a new game session, sets the current player turn to 0, and activates the game.
     * Prints a message indicating that the game session has started.
     *
     * @see #currentPlayerTurn
     * @see #isGameActive
     */
    @Override
    public void startGame() {
        currentPlayerTurn = 0;
        isGameActive = true;
        System.out.println("Game session started.");
    }

    /**
     * Advances to the next player's turn in the game.
     * If the game is active, it updates the currentPlayerTurn variable to represent the next player.
     */
    @Override
    public void nextTurn() {
//        if (isGameActive) {
//            currentPlayerTurn = (currentPlayerTurn + 1) % totalPlayers;
//        }
        if (isGameActive) {
            do {
                currentPlayerTurn = (currentPlayerTurn + 1) % totalPlayers;
            } while (worldMap.getCastles().get(currentPlayerTurn).isDestroyed());
        }
    }

    /**
     * Checks if it is the player's turn based on the current player ID.
     *
     * @return true if it is the player's turn, false otherwise
     */
    private boolean isPlayerTurn() {
        return currentPlayerTurn == players[currentPlayerTurn].getPlayerID();
    }

    /**
     * This method checks if the game is active and if so, ends the game.
     * If the game is active, it calls the endGame() method to close the current game session,
     * set the isGameActive flag to false, and print a message indicating that the game session ended.
     * If the game is not active, it simply prints a message indicating that no game is currently active.
     */
    @Override
    public void gameOver() {
        if (isGameActive) {
            System.out.println("Game over.");
            endGame();
        }
    }

    @Override
    public void gameWon() {
        if (isGameActive) {
            System.out.println("Game won.");
            endGame();
        }
    }

    /**
     * Ends the game session.
     * If there is an active game session, it will be closed and the current session will be set to null.
     * The game will be marked as inactive.
     * If there is no active game session, a message will be printed indicating that there is no game currently active.
     */
    @Override
    public void endGame() {
        if (isGameActive) {
            isGameActive = false;
            System.out.println("Game session ended.");
        } else {
            System.out.println("No game is currently active.");
        }
    }

    /**
     * Updates the current map with a new map.
     *
     * @param newMap The new map to update.
     */
    @Override
    public void updateMap(WorldMap newMap) {
        this.worldMap = newMap;
        System.out.println("Map has been updated.");
    }

    /**
     * Updates the game state.
     * <p>
     * This method checks if the game is currently active and then updates the game state accordingly.
     * If the game is active, it prints "Updating game state..." to the console.
     *
     */
    public void updateGameState() {
        if (isGameActive) {
            System.out.println("Updating game state...");
        }
    }

    private boolean checkIfWon(){
        System.out.println("checking if won...");
        System.out.println(totalPlayers);
        for (int i = 0; i < totalPlayers; i++){
            System.out.println(i + " " + worldMap.getCastles().get(i).isDestroyed());
            if (i == players[currentPlayerTurn].getPlayerID()) continue;
            if (!worldMap.getCastles().get(i).isDestroyed()){
                return false;
            }
        }
        return true;
    }
    /**
     * THIS METHOD IS ONE OF THE MOST IMPORTANT ONES FOR YOU TO USE
     * (it's not finished but is rather a concept how a turn looks like)
     * Plays a turn in the game. If the game is active and it is the player's turn,
     * the method runs the NPC actions for the player's castle and advances to the next turn.
     */
    @Override
    public void playTurn() {
        System.out.println(currentPlayerTurn);

        if (isGameActive && isPlayerTurn()) {
            if (players[currentPlayerTurn].getCastle().isDestroyed()) {
                gameOver();
                return;
            } else if (checkIfWon()) {
                gameWon();
                return;
            }
            /*
            //state for current player turn
            if(players[currentPlayerTurn].getCastle().getTroops().get("Worker")!=null) {
                for (Hero u : players[currentPlayerTurn].getCastle().getTroops().get("Worker")) {
                    u.getTile().returnResource(players[currentPlayerTurn].getCastle());
                }
            }
             */
            //todo implement player turn
            for (Castle castle : worldMap.getCastles()) {
                System.out.println("Castle " + castle.getOwnerID() + " isDestroyed: " + castle.isDestroyed());
            }
            /*
            //test
            player.getCastle().setHealthPoints(0);
            System.out.println(totalPlayers);
            worldMap.getCastles().get((player.getPlayerID() + 1) % totalPlayers).setDestroyed(true);

            for (Castle castle : worldMap.getCastles()) {
                System.out.println("Castle " + castle.getOwnerID() + " isDestroyed: " + castle.isDestroyed());
            }
            System.out.println(worldMap.getRemainingCastles());

            //updateMap(new WorldMap(200));
            */
            players[currentPlayerTurn].getCastle().updateMovementPoints();
            worldMap.runNPCActions(players[currentPlayerTurn].getCastle());
            if (isNetworkGame) {
                canMove = true;
            }
            if (players[currentPlayerTurn].getCastle().isDestroyed()) {
                gameOver();
                return;
            } else if (checkIfWon()) {
                gameWon();
                return;
            }
            nextTurn();
        }
    }

    private void assignCastle(int playerID) {
        players[currentPlayerTurn].setCastle(worldMap.getCastles().get(playerID));
    }

    /**
     * Sets the ID of the player.
     *
     * @param playerID the ID of the player
     */
    public void setPlayerID(int playerID) {
        players[currentPlayerTurn].setPlayerID(playerID);
        assignCastle(playerID);
    }

    private void setPlayersID(){
        for (int i = 0; i < 4; i++) {
            players[i].setPlayerID(i);
            players[i].setPlayerName(String.valueOf(i));
            if(i < totalPlayers)players[i].setCastle(worldMap.getCastles().get(i));
        }
    }
    /**
     * Sets the player name.
     *
     * @param name the name of the player
     */
    public void setPlayerName(String name) {
        players[currentPlayerTurn].setPlayerName(name);
    }

    /**
     * Returns a boolean value indicating whether the map needs to be refreshed.
     *
     * @return true if the map needs to be refreshed, false otherwise.
     */
    public boolean isRefreshMap() {
        return refreshMap;
    }

    /**
     * Sets the refreshMap flag to the specified value.
     *
     * @param refreshMap the boolean value to set the refreshMap flag to
     */
    public void setRefreshMap(boolean refreshMap) {
        this.refreshMap = refreshMap;
    }

    public Player getPlayer() {
        return players[currentPlayerTurn];
    }

    public int getTotalPlayers (){return totalPlayers;}
}
