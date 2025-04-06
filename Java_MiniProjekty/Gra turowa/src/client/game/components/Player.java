package client.game.components;

public class Player {
    /**
     * This variable represents the castle owned by a player.
     *
     * The castle object contains information about the player's castle, such as its location, size, and defenses.
     *
     * @see Player#getCastle()
     * @see Player#setCastle(Castle)
     */
    private Castle castle;
    /**
     * Represents the unique identifier for a player.
     */
    private int playerID;
    /**
     * The playerName variable stores the name of the player.
     */
    String playerName;

    /**
     * Retrieves the castle owned by the player.
     *
     * @return The castle owned by the player.
     */
    public Castle getCastle(){
        return castle;
    }

    /**
     * Returns the ID of the player.
     *
     * @return the ID of the player
     */
    public int getPlayerID() {
        return playerID;
    }

    /**
     * Sets the player ID.
     *
     * @param playerID the ID of the player
     */
    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    /**
     * Returns the name of the player.
     *
     * @return The player's name.
     */
    public String getPlayerName() {
        return playerName;
    }
    /**
     * Set the castle for the player.
     *
     * @param castle the castle to be set
     */
    public void setCastle(Castle castle) {
        this.castle = castle;
        this.castle.setOwnerID(playerID);
    }
    /**
     * Sets the player name.
     *
     * @param playerName the name of the player
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
