package client.game.components.unit.hero;

import client.game.components.map.tile.Tile;
import client.game.components.map.tile.Village;
import client.game.components.utils.exceptions.InvalidTileTypeException;
import client.game.components.utils.exceptions.VillagePlunderedException;
import client.game.components.Castle;
import client.game.components.unit.Unit;

import java.util.function.Function;

/**
 * class implementing unit hero
 */
public abstract class Hero extends Unit implements Cloneable{
    protected Castle owner;
    /**
     * The `movementPoints` variable represents the number of movement points
     * available for the unit. It is an integer value that determines how far
     * the unit can move on the map.
     *
     * The initial value of `movementPoints` is 5. When the unit moves, the
     * movement cost of the destination tile is subtracted from `movementPoints`.
     * If `movementPoints` becomes less than or equal to 0, the unit can no
     * longer move.
     *
     * Example usage:
     *
     * ```
     * Hero hero = new Hero(owner, damage, health, defence);
     * hero.walk(destination);
     * ```
     *
     * @see Tile#getMovementCost()
     * @see Tile#isOccupied()
     * @see Tile#setUnitOnTile(Unit)
     * @see Tile#freeTile()
     */
    protected int movementPoints = 5;

    /**
     * Initializes a new instance of the Hero class.
     *
     * @param owner   the Castle that owns the Hero
     * @param damage  the damage value of the Hero
     * @param health  the health value of the Hero
     * @param defence the defense value of the Hero
     */
    protected Hero(Castle owner, int damage, int health, int defence) {
        super(damage, health, defence);
        this.owner = owner;
        owner.addTroop(this);
    }

    /**
     * Moves the unit to the given destination tile if it is neighboring tile, has enough movement points and is not occupied.
     *
     * @param destination The destination tile to move to.
     * @return True if the unit successfully moved to the destination tile, false otherwise.
     */
    public boolean walk(Tile destination) {
        for (Tile tile : map.getNeighboringTiles(this.getCoordinates()[0], this.getCoordinates()[1])) {
            if (tile.equals(destination) && hasMovementPoints(destination) && !destination.isOccupied()) {
                this.getTile().freeTile();
                this.setTile(destination);
                destination.setUnitOnTile(this);
                this.movementPoints -= destination.getMovementCost();
                return true;
            }
        }
        return false;
    }

    /**
     * Plunders a village by setting it to a plundered state.
     *
     * @throws InvalidTileTypeException If the tile of the hero is not a village.
     * @throws VillagePlunderedException If the village has already been plundered.
     */
    public void plunder() {
        if (!(this.tile instanceof Village)) {
            throw new InvalidTileTypeException();
        }
        if (((Village) this.tile).isPlundered()) {
            throw new VillagePlunderedException();
        }
        ((Village) this.tile).setPlundered();
        this.tile.returnResource(owner);

    }
    /**
     * Creates and returns a copy of this Hero object.
     *
     * @return a copy of this Hero object
     * @throws CloneNotSupportedException if cloning is not supported for this object
     */
    @Override
    public Hero clone() throws CloneNotSupportedException {
        return (Hero) super.clone();
    }
    /**
     * Executes a special action on the given target unit.
     *
     * @param target the unit to perform the special action on
     */
    public abstract void specialAction(Unit target);

    /**
     * Performs an attack on the target unit.
     *
     * @param target the unit to attack
     * @throws InvalidTargetException if the target unit is a Hero and has the same owner as this unit
     */
    public abstract void attack(Unit target) throws InvalidTargetException;
    /**
     * Executes the provided action on the current hero instance.
     *
     * @param action the action to be executed
     * @param <T> the type of the result returned by the action
     */
    public <T> void doAction(Function<Hero, T> action) {
        action.apply(this);
    }
    /**
     * Retrieves the owner of the Hero.
     *
     * @return The Castle object that owns the Hero.
     */
    public Castle getOwner() {
        return owner;
    }
    /**
     * Checks if the given castle is owned by the same owner as the current hero.
     *
     * @param castle the castle to compare with the owner
     * @return true if the castle is owned by the same owner, false otherwise
     */
    protected boolean isSameOwner(Castle castle){
        return castle == owner;
    }

    /**
     * Attacks the specified castle.
     *
     * @param castle The castle to attack.
     */
    public void attackCastle(Castle castle) {
        for (Tile tile : map.getNeighboringTiles(this.getCoordinates()[0], this.getCoordinates()[1])) {
            if (tile instanceof Castle && ((Castle) tile).equals(castle) && !castle.isDestroyed()) {
                ((Castle) tile).setHealthPoints(((Castle) tile).getHealthPoints() - this.damage);
                if (((Castle) tile).getHealthPoints() <= 0) {
                    ((Castle) tile).destroy();
                }
            }
        }
    }

    public static class InvalidTargetException extends RuntimeException{
        InvalidTargetException(String e){
            super(e);
        }
    }
    /**
     * Checks if the unit has enough movement points to reach the destination tile.
     *
     * @param destination the destination tile to check
     * @return true if the unit has enough movement points, false otherwise
     */
    protected boolean hasMovementPoints(Tile destination){
        return movementPoints>destination.getMovementCost();
    }
    /**
     * Sets the movement points to the default value of 5 for a new turn.
     */
    public void setStatsForNewTurn(){
        this.movementPoints = 5;
    }
}