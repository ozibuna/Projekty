package client.game.components.unit.hero;

import client.game.components.map.tile.Tile;
import client.game.components.unit.Unit;
import client.game.components.Castle;

import java.util.Random;

/**
 * class implementing hero type Knight
 */
public class Knight extends Hero {
    private Random random = new Random();
    /**
     * The DAMAGE variable represents the amount of damage that a hero can inflict on an enemy unit or castle.
     * It is a private static final integer variable with a value of 20.
     */
    private static final int DAMAGE = 20;
    /**
     * The health of a unit.
     *
     * <p>This variable stores the health value for a unit. Health represents the current
     * amount of hit points a unit has. If the health reaches 0 or less, the unit is considered
     * to be dead.</p>
     *
     * @since 1.0
     */
    private static final int HEALTH = 130;
    /**
     * DEFENCE variable represents the defense points of a unit.
     *
     * The value of DEFENCE is set to 10.
     *
     * This variable is used in the Hero class and its subclasses to determine the defense points of a hero unit.
     * Defense points reduce the amount of damage received from attacks.
     */
    private static final int DEFENCE = 10;

    /**
     * Creates a new Knight hero with specified owner.
     *
     * @param owner the owner of the Knight hero
     */
    public Knight(Castle owner){
        super(owner,DAMAGE,HEALTH,DEFENCE);

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
        this.damage = 90;
        this.attack(target);
        this.damage = 30;
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
                    }else{
                        target.attack(this);
                    }
                }
            }
        }
    }
    @Override
    public Knight clone() throws CloneNotSupportedException {
        return (Knight) super.clone();
    }
}
