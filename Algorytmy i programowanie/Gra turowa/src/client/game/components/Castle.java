package client.game.components;

import client.game.components.map.WorldMap;
import client.game.components.map.tile.Tile;
import client.game.components.unit.hero.Hero;
import client.game.components.unit.hero.Worker;
import client.game.components.utils.exceptions.InsufficientResourcesException;
import client.game.components.utils.exceptions.MaxCastleLvlException;

import java.util.*;

public class Castle extends Tile{
    /**
     * Map that associates a resource type with its corresponding amount.
     */
    private final Map<Resources,Integer> resources;
    /**
     * The `defense` variable represents the defense value of a Castle object.
     * It is an integer value that determines the level of protection the Castle has
     * against enemy attacks.
     * The initial value of `defense` is 0. The defense value can be increased by
     * upgrading the Castle using the `upgradeCastle` method.
     *
     */
    private int defense;
    /**
     * The `healthPoints` variable represents the current health points of a Castle.
     * It is an integer value that determines the remaining health of the Castle.
     */
    private int healthPoints;
    /**
     * Represents the destroyed state of the castle.
     * If the castle is destroyed, it cannot perform any actions and cannot be upgraded.
     */
    private boolean isDestroyed;
    /**
     * The maximum number of health points for a castle.
     */
    private int maxHealthPoints;
    /**
     * Represents the level of a specific object or entity.
     */
    private Level level;
    /**
     * A private variable representing the troops owned by the castle.
     * The key of the map is the troop type, while the value is a list of heroes of that type.
     */
    private Map<String, ArrayList<Hero>> troops;

    private int ownerID;

    @Override
    public void returnResource(Castle castle) {
        castle.resources.put(Resources.GOLD, this.resources.get(Resources.GOLD));
        castle.resources.put(Resources.WOOD, this.resources.get(Resources.WOOD));
        castle.resources.put(Resources.STONE, this.resources.get(Resources.STONE));
        castle.resources.put(Resources.ENCHANTED_CACTUS, this.resources.get(Resources.ENCHANTED_CACTUS));
        castle.resources.put(Resources.HEART_OF_THE_SEA, this.resources.get(Resources.HEART_OF_THE_SEA));
        castle.resources.put(Resources.AMBER, this.resources.get(Resources.AMBER));
        castle.resources.put(Resources.HERBS, this.resources.get(Resources.HERBS));
    }

    public void setOwnerID(int playerID) {
        this.ownerID = playerID;
    }

    public int getOwnerID() {
        return this.ownerID;
    }

    public enum Level{
        ONE, TWO, THREE
    }
    /**
     * Upgrades the castle to the next level if the castle's level is not already at the maximum level.
     * Throws a MaxCastleLvlException if the castle is already at the maximum level.
     * Throws an InsufficientResourcesException if the castle does not have enough resources for the upgrade.
     *
     * @throws MaxCastleLvlException             if the castle is already at the maximum level
     * @throws InsufficientResourcesException   if the castle does not have enough resources for the upgrade
     */
    public void upgradeCastle() throws MaxCastleLvlException {
        if(level== Level.ONE&&hasEnoughResourcesForUpgrade()){
            level = Level.TWO;
            maxHealthPoints += maxHealthPoints/2;
            defense += defense/2;
            healthPoints = maxHealthPoints;
        }
        else if(level == Level.TWO&&hasEnoughResourcesForUpgrade()){
            level = Level.THREE;
            maxHealthPoints += maxHealthPoints/2;
            defense += defense/2;
            healthPoints = maxHealthPoints;
        }
        else if(level== Level.THREE){
            throw new MaxCastleLvlException();
        }else{
            throw new InsufficientResourcesException("You don't have enough resources");

        }
    }
    /**
     * Checks if the castle has enough resources to upgrade.
     *
     * @return true if the castle has enough resources, false otherwise
     */
    public boolean hasEnoughResourcesForUpgrade(){
        if (
                this.resources.get(Resources.GOLD) >= 300 &&
                        this.resources.get(Resources.WOOD) >= 50 &&
                        this.resources.get(Resources.STONE) >= 50 &&
                        this.resources.get(Resources.ENCHANTED_CACTUS) >= 3 &&
                        this.resources.get(Resources.HEART_OF_THE_SEA) >= 1 &&
                        this.resources.get(Resources.AMBER) >= 2 &&
                        this.resources.get(Resources.HERBS) >= 50
        ){
            this.resources.put(Resources.GOLD, this.resources.get(Resources.GOLD) - 300);
            this.resources.put(Resources.WOOD, this.resources.get(Resources.WOOD) - 50);
            this.resources.put(Resources.STONE, this.resources.get(Resources.STONE) - 50);
            this.resources.put(Resources.ENCHANTED_CACTUS, this.resources.get(Resources.ENCHANTED_CACTUS) - 3);
            this.resources.put(Resources.HEART_OF_THE_SEA, this.resources.get(Resources.HEART_OF_THE_SEA) - 1);
            this.resources.put(Resources.AMBER, this.resources.get(Resources.AMBER) - 2);
            this.resources.put(Resources.HERBS, this.resources.get(Resources.HERBS) - 50);
            return true;
        }
        return false;
    }
    /**
     * Constructs a Castle object with the given x and y coordinates.
     * Initializes the resources map with all resource types and their initial values set to zero.
     * Sets the level of the castle to Level.ONE.
     * Initializes the troops map.
     */
    public Castle(int x, int y){
        super(x, y);
        this.resources = new HashMap<>(7);
        this.level = Level.ONE;
        this.isDestroyed = false;
        troops = new HashMap<>();
        fillResources();
        maxHealthPoints = 100;
        healthPoints = maxHealthPoints;
        //todo: set max health points and defense
    }

    /**
     * Fills the resources map with initial values.
     */
    private void fillResources(){
        for (Resources resource : Resources.values()){
            this.resources.put(resource, 500);
        }
    }
    /**
     * Sets the castle to a destroyed state.
     */
    public void destroy(){
        this.isDestroyed = true;
    }

    /**
     * Retrieves the amount of a specific resource in the castle.
     *
     * @param resourceType the type of resource to retrieve the amount of
     * @return the amount of the specified resource in the castle
     */
    public int getResourceAmount(Resources resourceType) {
        return resources.get(resourceType);
    }

    /**
     * Adjusts the amount of a specific resource.
     *
     * @param resourceType the type of resource to be adjusted
     * @param amount the amount to be added or subtracted from the resource
     * @throws InsufficientResourcesException if the new amount of the resource is less than zero
     */
    public void adjustResource(Resources resourceType, Integer amount){
        int currentAmount = resources.getOrDefault(resourceType, 0);
        int newAmount = currentAmount + amount;
        if (newAmount < 0) {
            throw new InsufficientResourcesException("You don't have enough resources");
        }
        resources.put(resourceType, newAmount);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Castle castle = (Castle) object;
        return defense == castle.defense && healthPoints == castle.healthPoints && isDestroyed == castle.isDestroyed && maxHealthPoints == castle.maxHealthPoints && Objects.equals(resources, castle.resources) && level == castle.level;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), resources, defense, healthPoints, isDestroyed, maxHealthPoints, level);
    }
    /**
     * Adds a new Hero to the troops of the Castle. If the Hero's class name already exists as a key in the troops map, the Hero is added to the corresponding ArrayList. If the Hero
     *'s class name does not exist as a key, a new entry with the class name as key and a new ArrayList containing the Hero as value is created.
     *
     * @param hero the Hero to be added to the troops of the Castle
     */
    public void addTroop(Hero hero){
        if(troops.containsKey(hero.getClass().getSimpleName())){
            troops.get(hero.getClass().getSimpleName()).add(hero);
        }
        else{
            troops.put(hero.getClass().getSimpleName(), new ArrayList<>(List.of(hero)));
        }
    }
    public Tile findEmptyAdjacentTile(WorldMap worldMap) {
        // Check adjacent tiles (up, down, left, right) for an empty one
        int x = this.getX();
        int y = this.getY();
        Tile tile;

        // Check above
        if (worldMap.isValidTile(x - 1, y)) {
            tile = worldMap.getTile(x - 1, y);
            if (tile.getUnitOnTile() == null) {
                return tile;
            }
        }

        // Check below
        if (worldMap.isValidTile(x + 1, y)) {
            tile = worldMap.getTile(x + 1, y);
            if (tile.getUnitOnTile() == null) {
                return tile;
            }
        }

        // Check left
        if (worldMap.isValidTile(x, y - 1)) {
            tile = worldMap.getTile(x, y - 1);
            if (tile.getUnitOnTile() == null) {
                return tile;
            }
        }

        // Check right
        if (worldMap.isValidTile(x, y + 1)) {
            tile = worldMap.getTile(x, y + 1);
            if (tile.getUnitOnTile() == null) {
                return tile;
            }
        }

        return null; // No empty adjacent tile found
    }
    public boolean hasEnoughResourcesForAddingTroop(String hero) {
        switch (hero) {
            case "Archer":
                if (this.resources.get(Resources.WOOD) >= 10) {
                    this.resources.put(Resources.WOOD, this.resources.get(Resources.WOOD) - 10);
                    return true;
                }
                break;
            case "Druid":
                if (this.resources.get(Resources.HERBS) >= 10) {
                    this.resources.put(Resources.HERBS, this.resources.get(Resources.HERBS) - 10);
                    return true;
                }
                break;
            case "Healer":
                if (this.resources.get(Resources.HEART_OF_THE_SEA) >= 10) {
                    this.resources.put(Resources.HEART_OF_THE_SEA, this.resources.get(Resources.HEART_OF_THE_SEA) - 10);
                    return true;
                }
                break;
            case "Knight":
                if (this.resources.get(Resources.STONE) >= 10) {
                    this.resources.put(Resources.STONE, this.resources.get(Resources.STONE) - 10);
                    return true;
                }
                break;
            case "Warlock":
                if (this.resources.get(Resources.AMBER) >= 10) {
                    this.resources.put(Resources.AMBER, this.resources.get(Resources.AMBER) - 10);
                    return true;
                }
                break;
            case "Wizard":
                if (this.resources.get(Resources.ENCHANTED_CACTUS) >= 10) {
                    this.resources.put(Resources.ENCHANTED_CACTUS, this.resources.get(Resources.ENCHANTED_CACTUS) - 10);
                    return true;
                }
                break;
            case "Worker":
                if (this.resources.get(Resources.GOLD) >= 10) {
                    this.resources.put(Resources.GOLD, this.resources.get(Resources.GOLD) - 10);
                    return true;
                }
                break;
        }
        return false;
    }


    /**
     * Retrieves the resources available in the castle.
     *
     * @return a map containing the resources and their corresponding amounts in the castle
     */
    public Map<Resources, Integer> getResources() {
        return resources;
    }

    /**
     * Retrieves the defense value of the castle.
     *
     * @return the defense value of the castle
     */
    public int getDefense() {
        return defense;
    }

    /**
     * Sets the defense value of the castle.
     *
     * @param defense the new defense value to be set
     */
    public void setDefense(int defense) {
        this.defense = defense;
    }

    /**
     * Retrieves the current health points of the castle.
     *
     * @return the current health points of the castle
     */
    public int getHealthPoints() {
        return healthPoints;
    }

    /**
     * Sets the health points of the castle.
     *
     * @param healthPoints the new health points value for the castle
     */
    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    /**
     * Returns whether the castle is destroyed or not.
     *
     * @return true if the castle is destroyed, false otherwise
     */
    public boolean isDestroyed() {
        return isDestroyed;
    }

    /**
     * Sets the destroyed state of the castle.
     *
     * @param destroyed true if the castle is destroyed, false otherwise
     */
    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }

    /**
     * Retrieves the maximum health points of the castle.
     *
     * @return the maximum health points of the castle
     */
    public int getMaxHealthPoints() {
        return maxHealthPoints;
    }

    /**
     * Sets the maximum health points of the castle.
     *
     * @param maxHealthPoints the maximum health points to set
     */
    public void setMaxHealthPoints(int maxHealthPoints) {
        this.maxHealthPoints = maxHealthPoints;
    }

    /**
     * Retrieves the level of the castle.
     *
     * @return the level of the castle
     */
    public Level getLevel() {
        return level;
    }

    /**
     * Sets the level of the castle.
     *
     * @param level the level to set for the castle
     */
    public void setLevel(Level level) {
        this.level = level;
    }

    /**
     * Retrieves the troops of the castle.
     *
     * @return a map containing the troops of the castle. The map has a key of type String, representing the troop type,
     *         and a value of type ArrayList<Hero>, representing the list of heroes of that troop type.
     *         Each troop type is associated with a list of heroes belonging to that troop type.
     */
    public Map<String, ArrayList<Hero>> getTroops() {
        return troops;
    }

    /**
     * Sets the troops map of the castle.
     *
     * @param troops the map of troop types and their corresponding lists of heroes
     */
    public void setTroops(Map<String, ArrayList<Hero>> troops) {
        this.troops = troops;
    }
    /**
     * Updates the movement points of all troops in the castle for a new turn.
     * After this method is called, all troops will have their movement points set to the default value of 5.
     */
    public void updateMovementPoints(){
        troops.forEach((_, value) -> value.forEach(Hero::setStatsForNewTurn));
    }
}
