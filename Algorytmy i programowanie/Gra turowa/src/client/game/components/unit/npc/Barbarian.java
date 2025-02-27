package client.game.components.unit.npc;

import client.game.components.unit.hero.Hero;
import client.game.components.unit.Unit;
import client.game.components.Castle;
import client.game.components.Resources;

public class Barbarian extends NPC {
    /**
     * The `level` variable represents the level of a Non-Playable Character (NPC). The level determines the difficulty or strength of the NPC.
     *
     * The `level` variable is a final variable, meaning once it is assigned a value, it cannot be modified.
     */
    private final Level level;
    /**
     * The IDLE_DAMAGE variable represents the amount of damage that is inflicted by the Barbarian NPC when it performs an idle action.
     * The amount of damage is set to a constant value of 3.
     *
     * @see Barbarian#idleAction(Castle)
     */
    private static final int IDLE_DAMAGE = 3;
    /**
     * The Barbarian class represents a type of non-player character (NPC) in the game.
     * It extends the NPC class and implements various actions and behaviors specific to barbarians.
     */
    public Barbarian(int range, Level level){
        super(range, 15, 80, 10);
        this.level = level;
        adjustHealth();
    }

    /**
     * Attacks the target unit by reducing its health by the specified amount of damage. If the target's health
     * reaches or falls below 0, the target unit is killed.
     *
     * @param target  the unit to attack
     * @param damage  the amount of damage to inflict on the target unit
     */
    public void attack(Hero target, int damage) {
        target.setHealth(target.getHealth() - damage);
        if (target.getHealth() <= 0) {
            target.kill();
        }
    }
    /**
     * Performs an attack on the target unit.
     *
     * @param target the unit to attack
     * @throws Hero.InvalidTargetException if the target unit is a Hero and has the same owner as this unit
     */
    @Override
    public void attack(Unit target){
        target.setHealth(target.getHealth() - (calculateDamage() - target.getDefense()));
        if (target.getHealth() <= 0) {
            target.kill();
        }
    }
    /**
     * Gives out resources to the given castle.
     *
     * @param castle the castle to give out resources to
     */
    @Override
    public void giveOutResources(Castle castle){
        int stone  = calculateResources();
        int wood = calculateResources();
        castle.adjustResource(Resources.WOOD, wood);
        castle.adjustResource(Resources.STONE, stone);
    }
    /**
     * Perform idle actions for the Barbarian NPC.
     * This method gets neighbouring heroes and attacks them with a specified idle damage.
     * It also shouts during the idle action, damaging neighbouring heroes.
     *
     * @param castle The castle to perform idle actions for.
     */
    @Override
    public void idleAction(Castle castle) {
        getNeighbouringHeroes(castle);
        shout();
    }

    /**
     * Attacks the specified hero with the given damage.
     */
    private void shout(){
        for (Hero hero : neighbouringHeroes){
            attack(hero, IDLE_DAMAGE);
        }
    }

    /**
     * Calculates the damage based on the level of the NPC.
     *
     * @return the calculated damage
     */
    private int calculateDamage(){
        int damage = 0;
        switch (level){
            case EASY ->  damage = random.nextInt(1,15);
            case MEDIUM -> damage = random.nextInt(16, 30);
            case HARD -> damage =  random.nextInt(31, 50);
        }
        return damage;
    }

    /**
     * Adjusts the maximum health and current health of the NPC based on its level.
     * If the level is MEDIUM, the health is doubled.
     * If the level is HARD, the health is tripled.
     * If the level is any other value, no adjustment is made.
     */
    private void adjustHealth(){
        switch (level){
            case MEDIUM:
                maxHealth = health * 2;
                health = health * 2;
                break;
            case HARD:
                maxHealth = health * 3;
                health = health * 3;
                break;
            default:
                break;
        }
    }
    /**
     * Calculates the amount of resources based on the level of the NPC.
     *
     * @return The calculated amount of resources.
     */
    private int calculateResources(){
        int resources = 0;
        switch (level){
            case EASY ->  resources = random.nextInt(50,100);
            case MEDIUM -> resources = random.nextInt(150, 200);
            case HARD -> resources =  random.nextInt(250, 300);
        }
        return resources;
    }
}
