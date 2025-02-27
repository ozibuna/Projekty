package client.game.components.unit.hero;

import client.game.components.map.tile.Tile;
import client.game.components.unit.Unit;
import client.game.components.Castle;

import java.util.Random;

/**
 * class implementing hero type Archer
 */
public class Archer extends Hero {
    private Random random = new Random();
    /**
     * The DAMAGE variable represents the amount of damage a unit can inflict on another unit.
     * It is an integer value that determines the base attack strength of the unit.
     *
     * The initial value of DAMAGE is 40.
     */
    private static final int DAMAGE = 40;
    /**
     * The HEALTH variable represents the current health value of a Hero.
     * It is an integer value that determines how much health the hero has.
     *
     * The initial value of HEALTH is 70. The health value can be updated
     * during the course of the program based on the hero's interactions
     * with other units or actions taken.
     *
     */
    private static final int HEALTH = 70;
    /**
     * The DEFENCE variable represents the defense value of a Hero.
     * It is an integer value that determines how much damage the Hero can resist.
     * The initial value of DEFENCE is 5.
     */
    private static final int DEFENCE = 5;

    /**
     * Constructs an Archer object with the specified Castle owner.
     *
     * @param owner the Castle object that owns this Archer
     */
    public Archer(Castle owner){
        super(owner,DAMAGE,HEALTH,DEFENCE);
        this.setHealth(health);

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
        this.attack(target);
        if(validateArcherRange(target)){
            int temp = random.nextInt(20) - 10;
            for (Tile tile : map.getNeighboringTiles(this.getCoordinates()[0], this.getCoordinates()[1])) {
                if(tile.isOccupied() && tile.getUnitOnTile().isAlive()){
                    tile.getUnitOnTile().setHealth(tile.getUnitOnTile().getHealth() - (this.damage/2 + temp/2) - target.getDefense()/2);
                    if (tile.getUnitOnTile().getHealth() <= 0) {
                        tile.getUnitOnTile().kill();
                    }
                }
            }
        }
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
        if(validateArcherRange(target)){
            random = new Random();
            int temp = random.nextInt(20) - 10;
            if (target.isAlive()) {
                target.setHealth(target.getHealth() - (this.damage + temp) - target.getDefense());
                if (target.getHealth() <= 0) {
                    target.kill();
                }else{
                    target.attack(this);
                }
            }
        }
    }
    /**
     * Method to validate if the target is within range of the Archer's attack.
     *
     * @param target the unit to validate its range against
     * @return true if the target is within range, false otherwise
     */
    private boolean validateArcherRange(Unit target){
        for (Tile tile : map.getNeighboringTiles(this.getCoordinates()[0], this.getCoordinates()[1])) {
            for(Tile t : map.getNeighboringTiles(tile.getCoordinates()[0], tile.getCoordinates()[1])){
                if(target.getTile().equals(t)){
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public Archer clone() throws CloneNotSupportedException {
        return (Archer) super.clone();
    }
}
