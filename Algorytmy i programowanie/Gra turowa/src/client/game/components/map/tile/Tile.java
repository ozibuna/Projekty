package client.game.components.map.tile;

import client.game.components.map.WorldMap;
import client.game.components.unit.Unit;
import client.game.components.Castle;
import client.game.components.Resources;

import java.io.Serializable;
import java.util.Objects;

/**
 * A single tile of the map, holds its coordinates, terrain and unit (if present)
 */
public abstract class Tile implements Serializable {
    /**
     *
     */
    protected int x;
    /**
     * The Y-coordinate of the tile on the map.
     */
    protected int y;
    /**
     * The movement cost of a Tile in a map. Determines the cost to move onto the Tile.
     */
    protected int movementCost;
    /**
     * Represents the unit currently occupying a tile.
     *
     * @see Tile
     * @see Unit
     */
    protected Unit unitOnTile;
    /**
     * Represents a resource in the game.
     *
     * The {@code Resources} enum defines various types of resources that can be used in the game, such as gold, wood, stone, etc.
     * These resources are used for various purposes, such as creating units or constructing buildings.
     *
     * Resources are associated with tiles on the game map, and can be extracted by players who own those tiles.
     * Each resource has a specific tile on the map where it is located.
     *
     * Resources can be collected by players by performing specific actions or by owning certain buildings.
     * Once collected, resources can be used to perform various actions, such as constructing units or buildings, upgrading existing units or buildings, etc.
     *
     * Each resource has a specific amount associated with it, which represents the current quantity of that resource available to the player.
     * The amount can be increased or decreased based on game events or player actions.
     *
     * This enumeration represents the available types of resources in the game.
     * Each resource type has a unique name and can be identified by calling its {@code name()} method.
     *
     * Example usage:
     * <pre>
     *    Resources.WOOD; // Accessing the WOOD resource
     *    Resources.GOLD; // Accessing the GOLD resource
     *    Resources.STONE; // Accessing the STONE resource
     * </pre>
     *
     * @see Tile
     * @see Unit
     * @see WorldMap
     */
    protected Resources resource;
    /**
     * Constructs a new Tile object with the given coordinates.
     *
     * @param x The x-coordinate of the tile.
     * @param y The y-coordinate of the tile.
     */
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }
    /**
     * Retrieves the unit located on the tile.
     *
     * @return the unit located on the tile, or null if no unit is present
     */
    public Unit getUnitOnTile(){
        return unitOnTile;
    }
    /**
     * Sets the unit on the tile.
     *
     * @param unit the unit to set on the tile
     */
    public void setUnitOnTile(Unit unit){
        this.unitOnTile = unit;
    }
    /**
     * Retrieves the coordinates of the tile.
     *
     * @return an array of two integers representing the x and y coordinates of the tile
     */
    public int[] getCoordinates(){
        return new int[]{x,y};
    }
    /**
     * Checks if the tile is occupied by a unit.
     *
     * @return true if the tile is occupied by a unit, false otherwise
     */
    public boolean isOccupied(){
        return unitOnTile!=null;
    }
    /**
     * Frees the tile by removing the unit from it.
     */
    public void freeTile(){
        this.unitOnTile = null;
    }
    /**
     * Returns a resource to the given Castle from the Tile.
     *
     * @param castle the Castle to return the resource to
     *
     */
    public abstract void returnResource(Castle castle);
    /**
     * Returns the movement cost of the tile.
     *
     * @return The movement cost of the tile.
     */
    public int getMovementCost(){
        return movementCost;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Tile tile = (Tile) object;
        return x == tile.x && y == tile.y && movementCost == tile.movementCost && Objects.equals(unitOnTile, tile.unitOnTile) && resource == tile.resource;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, movementCost, unitOnTile, resource);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Resources getResource() {
        return resource;
    }
}
