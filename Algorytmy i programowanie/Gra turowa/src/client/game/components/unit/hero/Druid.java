package client.game.components.unit.hero;

import client.game.components.map.tile.Tile;
import client.game.components.unit.Unit;
import client.game.components.Castle;

import java.util.Random;

/**
 * class implementing hero type Druid
 */
public class Druid extends Hero {
    private Random random = new Random();
    /**
     * The `DAMAGE` variable represents the amount of damage that a Druid hero can deal in an attack.
     * It is a constant integer value that does not change once initialized.
     */
    private static final int DAMAGE = 10;
    /**
     * The `HEALTH` variable represents the initial health value of a hero unit.
     * It is an integer value that determines the maximum health points of the hero.
     *
     * The initial value of `HEALTH` is 100. This value can be modified throughout the execution of the program.
     *
     * @see Hero#getHealth()
     * @see Hero#setHealth(int)
     */
    private static final int HEALTH = 100;
    /**
     * The DEFENCE variable represents the defense value of a Hero unit.
     * It is an integer that determines the amount of damage reduction the unit has when attacked.
     *
     * The initial value of DEFENCE is 20.
     * @see Hero#getDefense()
     * @see Hero#attack(Unit)
     */
    private static final int DEFENCE = 20;

    /**
     * class implementing hero type Druid
     */
    public Druid(Castle owner){
        super(owner,DAMAGE,HEALTH,DEFENCE);
    }

    /**
     * Performs a special action for the unit.
     *
     * @param target the target unit for the special action
     * @throws InvalidTargetException if target is an enemy unit
     * @throws RuntimeException if cloning fails
     */
    @Override
    public void specialAction(Unit target) {
        if(target instanceof Hero){
            if(!((Hero) target).isSameOwner(this.getOwner())){
                throw new InvalidTargetException("you can't clone enemy troop");
            }
        }
        if(movementPoints<=2) {
            return;
        }
        movementPoints-=2;
        for (Tile tile : map.getNeighboringTiles(this.getCoordinates()[0], this.getCoordinates()[1])) {
            if(target.equals(tile.getUnitOnTile())){
                for (Tile t : map.getNeighboringTiles(target.getCoordinates()[0], target.getCoordinates()[1])) {
                    if (t.getUnitOnTile() == null) {
                        try {
                            assert target instanceof Hero;
                            Hero clone = ((Hero) target).clone();
                            clone.doAction(Hero-> clone.walk(t));
                            return;
                        } catch (CloneNotSupportedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }

    }

    @Override
    public void attack(Unit target) {
        if(target instanceof Hero){
            if(!((Hero) target).isSameOwner(this.getOwner())){
                throw new InvalidTargetException("you can't clone enemy troop");
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
                    }else{
                        target.attack(this);
                    }
                }
            }
        }
    }
    @Override
    public Druid clone() throws CloneNotSupportedException {
        return (Druid) super.clone();
    }
}
