package client.game.components.unit.hero;

import client.game.components.map.tile.Tile;
import client.game.components.unit.Unit;
import client.game.components.Castle;

/**
 * class implementing hero type Healer
 */
public class Healer extends Hero {
    /**
     * The DAMAGE variable represents the damage value of a Hero unit.
     * It is a constant integer value that determines the amount of damage
     * the Hero can inflict on other units or objects.
     *
     * Example usage:
     *
     * ```
     * int damage = DAMAGE;
     * // Use damage in calculations or assignments
     * ```
     *
     * @see Hero
     */
    private static final int DAMAGE = 30;
    /**
     * The `HEALTH` variable represents the initial health value of a hero.
     * It is an integer value that determines the maximum health of the hero.
     *
     * The initial value of `HEALTH` is 30. This value may be modified by other
     * classes or methods as needed.
     *
     * Example usage:
     *
     * ```
     * int maxHealth = HEALTH;
     * ```
     */
    private static final int HEALTH = 30;
    /**
     * This variable represents the defence value of a unit.
     *
     * The `DEFENCE` variable is an integer value that determines the amount of damage
     * that a unit can mitigate when being attacked by an enemy unit. The higher the
     * defence value, the less damage the unit takes.
     *
     * @see Hero#attack(Unit)
     * @see Warlock#attack(Unit)
     * @see Archer#attack(Unit)
     */
    private static final int DEFENCE = 30;
    /**
     * Creates a new instance of the Healer class.
     *
     * @param owner the Castle that owns the Healer
     *
     * @see Castle
     */
    public Healer(Castle owner){
        super(owner,DAMAGE,HEALTH,DEFENCE);
    }
    @Override
    public void specialAction(Unit target) {
        if(movementPoints<=2) {
            return;
        }
        this.attack(target);
        for (Tile tile : map.getNeighboringTiles(this.getCoordinates()[0], this.getCoordinates()[1])) {
            if (target.getTile().equals(tile)) {
                for(Tile t :map.getNeighboringTiles(tile.getCoordinates()[0],tile.getCoordinates()[1])){
                    Unit unit = t.getUnitOnTile();
                    if(unit.getMaxHealth()-unit.getHealth()>this.damage){
                        unit.setHealth(unit.getHealth()+this.damage);
                    }else{
                        unit.setHealth(unit.getMaxHealth());
                    }
                }
            }
        }
    }

    @Override
    public void attack(Unit target) {
        if(movementPoints<=2) {
            return;
        }
        movementPoints-=2;
        for (Tile tile : map.getNeighboringTiles(this.getCoordinates()[0], this.getCoordinates()[1])) {
            if (target.getTile().equals(tile)) {
                if(target.getMaxHealth()-target.getHealth()>this.damage){
                    target.setHealth(target.getHealth()+this.damage);
                } else{
                    target.setHealth(target.getMaxHealth());
                }
            }
        }
    }
    @Override
    public Healer clone() throws CloneNotSupportedException {
        return (Healer) super.clone();
    }
}
