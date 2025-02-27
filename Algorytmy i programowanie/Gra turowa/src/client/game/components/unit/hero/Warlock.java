package client.game.components.unit.hero;

import client.game.components.map.tile.Tile;
import client.game.components.unit.Unit;
import client.game.components.Castle;

import java.util.Random;

/**
 * class implementing hero type Warlock
 */
public class Warlock extends Hero {
    private final Random random = new Random();
    /**
     * The amount of damage inflicted by a unit.
     */
    private static final int DAMAGE = 70;
    /**
     * The amount of health a hero or unit has.
     * This value is set to 120.
     */
    private static final int HEALTH = 120;
    /**
     * Represents the defense of a unit.
     */
    private static final int DEFENCE = 10;

    /**
     * Constructor for the Warlock class.
     *
     * @param owner the Castle that owns the Warlock
     */
    public Warlock(Castle owner) {
        super(owner, DAMAGE, HEALTH, DEFENCE);
    }


    @Override
    public void specialAction(Unit target) {
        if (target instanceof Hero) {
            if (target != this) {
                throw new InvalidTargetException("You can upgrade only yourself");
            }
        }
        if (movementPoints <= 2) {
            return;
        }
        movementPoints -= 2;
        if (this.damage != 100) {
            this.damage += 5;
        } else {
            throw new InvalidTargetException("You can't use this skill because your damage is already maximized");
        }
    }

    @Override
    public void attack(Unit target) {
        if (target instanceof Hero) {
            if (this.isSameOwner(((Hero) target).getOwner())) {
                throw new InvalidTargetException("You can't attack your own troops moron");
            }
        }
        if (movementPoints <= 2) {
            return;
        }
        movementPoints -= 2;
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
    public Warlock clone() throws CloneNotSupportedException {
        return (Warlock) super.clone();
    }
}
