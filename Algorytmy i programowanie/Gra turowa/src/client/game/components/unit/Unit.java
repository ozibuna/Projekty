package client.game.components.unit;

import client.game.components.map.WorldMap;
import client.game.components.map.tile.Tile;
import client.game.components.map.tile.Village;
import client.game.components.unit.hero.Hero;

import java.io.Serializable;
public abstract class Unit implements Serializable {
    /**
     * Protected variable that represents the name of the unit.
     * @see Unit
     * @see Unit#name
     */
    protected String name;
    /**
     * The current health of the unit.
     */
    protected int health;
    /**
     * The maximum health of a unit.
     */
    protected int maxHealth;
    /**
     * Represents the amount of damage that a Unit can deal.
     */
    protected int damage;
    /**
     * The WorldMap object associated with this Unit.
     */
    protected WorldMap map;
    /**
     * The defense value of the Unit. Determines how much damage is reduced when the unit is attacked.
     */
    protected int defense;
    /**
     * A single tile of the map, holds its coordinates, terrain and unit (if present)
     */
    protected Tile tile;
    /**
     * Determines if the unit is alive.
     *
     * @return true if the unit is alive, false otherwise.
     */
    private boolean isAlive;
    /**
     * Initializes a new Unit object with the given damage, health, and defense values.
     *
     * @param damage  the damage value of the Unit
     * @param health  the health value of the Unit
     * @param defense the defense value of the Unit
     */
    protected Unit(int damage, int health, int defense){
        this.damage = damage;
        this.health = health;
        this.maxHealth = health;
        this.isAlive = true;
        this.defense = defense;
    }
    /**
     * Performs an attack on the target unit.
     *
     * @param target the unit to attack
     * @throws Hero.InvalidTargetException if the target unit is a Hero and has the same owner as this unit
     */
    public abstract void attack(Unit target);
    /**
     * Sets the health of the unit.
     *
     * @param health the new health value
     */
    public void setHealth(int health){
        this.health = health;
    }
    /**
     * Returns the current health of the unit.
     *
     * @return the current health of the unit
     */
    public int getHealth(){
        return this.health;
    }
    /**
     * Gets the maximum health of this unit.
     *
     * @return The maximum health of this unit.
     */
    public int getMaxHealth(){
        return this.maxHealth;
    }
    /**
     * Sets the damage value for the Unit.
     *
     * @param damage the new damage value for the Unit
     */
    public void setDamage(int damage){
        this.damage = damage;
    }
    /**
     * Returns the damage value of this unit.
     *
     * @return the damage value of this unit
     */
    public int getDamage(){
        return damage;
    }
    /**
     * Returns the defense value of the Unit.
     *
     * @return the defense value
     */
    public int getDefense(){
        return defense;
    }
    /**
     * Sets the defense value of the Unit.
     *
     * @param defense the new defense value to set
     */
    public void setDefense(int defense){
        this.defense = defense;
    }
    /**
     * Sets the status of the unit's alive state.
     *
     * @param alive the boolean value indicating whether the unit is alive
     */
    public void setIsAlive(boolean alive){
        this.isAlive = alive;
    }
    /**
     * Sets the given tile for the unit.
     *
     * @param tile The tile to set for the unit.
     */
    public void setTile(Tile tile){
        this.tile = tile;
    }


    /**
     * Returns the tile on which the unit is located.
     *
     * @return the tile on which the unit is located
     */
    public Tile getTile(){
        return this.tile;
    }
    /**
     * Kills the unit by setting its "isAlive" field to false and freeing the tile it occupies.
     */
    public void kill(){
        if(this.getTile() instanceof Village){
            ((Village) this.getTile()).setCanBePlundered();
        }
        this.isAlive = false;
        tile.freeTile();
    }

    /**
     * Determines if the unit is alive.
     * @return true if the unit is alive, false otherwise.
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Retrieves the WorldMap object associated with this Unit.
     *
     * @return The WorldMap object.
     */
    public WorldMap getMap() {
        return map;
    }

    /**
     * Sets the WorldMap for the Unit.
     *
     * @param map the WorldMap to set
     */
    public void setMap(WorldMap map) {
        this.map = map;
    }

    /**
     * Returns the coordinates of the tile associated with this unit.
     *
     * @return an array of two integers representing the x and y coordinates of the tile
     */
    public int[] getCoordinates() {
        return tile.getCoordinates();
    }

}
