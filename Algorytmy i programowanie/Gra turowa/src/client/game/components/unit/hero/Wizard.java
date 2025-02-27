package client.game.components.unit.hero;

import client.game.components.map.tile.Tile;
import client.game.components.map.tile.Water;
import client.game.components.unit.Unit;
import client.game.components.Castle;

import java.util.Random;

/**
 * Special ability of the wizard is that he can walk on water
 */

public class Wizard extends Hero{
    private final Random random = new Random();
    /**
     * The `DAMAGE` variable represents the damage value of a hero.
     * It is an integer value that determines the amount of damage
     * the hero can inflict on its target.
     *
     * The initial value of `DAMAGE` is 50.
     *
     * Example usage:
     *
     * ```java
     * int damage = DAMAGE;
     * ```
     *
     * @see Hero#attack(Unit)
     * @see Hero#specialAction(Unit)
     */
    private static final int DAMAGE = 50;
    /**
     * The HEALTH variable represents the initial health value of a Hero or Unit object.
     */
    private static final int HEALTH = 100;
    /**
     * The `DEFENCE` variable represents the defense value of a Hero.
     *
     * The initial value of `DEFENCE` is 10 and it is a constant that cannot be changed.
     *
     * Example usage:
     *
     * ```
     * int defence = DEFENCE;
     * ```
     *
     * @see Hero#Hero(Castle, int, int, int)
     */
    private static final int DEFENCE = 10;
    /**
     * Wizard class represents a type of Hero that has the special ability to walk on water.
     * It inherits from the Hero class.
     */
    public Wizard(Castle owner){
        super(owner,DAMAGE,HEALTH,DEFENCE);
        this.setHealth(health);
    }

    @Override
    public boolean walk(Tile destination){
        if (destination instanceof Water || super.walk(destination)) {
            int calculatedMovementCost = destination.getMovementCost() / 100000 * 3;
            if (calculatedMovementCost < this.movementPoints){
                this.getTile().freeTile();
                this.setTile(destination);
                destination.setUnitOnTile(this);
                this.movementPoints -= calculatedMovementCost;
                destination.returnResource(owner);
            }
            return true;
        }
        return false;
    }
    
    @Override
    public void attack(Unit target) {
        if(target instanceof Hero){
            if (this.isSameOwner(((Hero) target).getOwner())){
                throw new InvalidTargetException("You can't attack your own troops moron");
            }
        }
        if(movementPoints<=2) {
            return;
        }
        movementPoints-=2;
        for (Tile tile : map.getNeighboringTiles(this.getCoordinates()[0], this.getCoordinates()[1])) {
            if (target.getTile().equals(tile)) {
                int temp = random.nextInt(20) - 10;
                if (target.isAlive()) {
                    target.setHealth(target.getHealth() - (this.damage + temp) - target.getDefense());
                    if (target.getHealth() <= 0) {
                        target.kill();
                    }
                }
            }
        }
    }

    @Override
    public void specialAction(Unit target) {
        if(target instanceof Hero){
            if (this.isSameOwner(((Hero) target).getOwner())){
                throw new InvalidTargetException("You can't attack your own troops moron");
            }
        }
        if(movementPoints<=2) {
            return;
        }
        movementPoints-=2;
        for (Tile tile : map.getNeighboringTiles(this.getCoordinates()[0], this.getCoordinates()[1])) {
            if (target.getTile().equals(tile)) {
                if (target.isAlive()) {
                    target.setHealth(target.getHealth() - (20));
                    this.setHealth(this.getHealth() + 20);
                    if (target.getHealth() <= 0) {
                        target.kill();
                    }
                    if(this.getHealth() >= this.getMaxHealth()){
                        this.setHealth(this.getMaxHealth());
                    }
                }
            }
        }
    }
    @Override
    public Wizard clone() throws CloneNotSupportedException {
        return (Wizard) super.clone();
    }
}
