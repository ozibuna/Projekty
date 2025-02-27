package client.game.components.unit.npc;

import client.game.components.unit.hero.Hero;
import client.game.components.unit.Unit;
import client.game.components.utils.exceptions.InsufficientResourcesException;
import client.game.components.Castle;
import client.game.components.Resources;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Trader extends NPC {
    /**
     * The `random` variable represents an instance of the Random class.
     * It is used to generate random numbers in the application.
     */
    private static Random random;

    /**
     * The `prices` variable represents a map that contains the prices of different resources.
     * It is a private instance variable of the Trader class.
     * The map is used to store the price per unit of each resource, where the Resources enum is used as the key and the price is an integer value.
     *
     * @see Trader
     * @see Resources
     */
    private Map<Resources, Integer> prices;
    /**
     * The `inventory` variable represents the inventory of a Trader. It is a mapping of Resources to integers,
     * where each Resource represents a specific item and each integer represents the quantity of that item in the inventory.
     *
     * The initial value of the inventory variable is set during the construction of a Trader object using the Trader(int range) constructor,
     * and is initialized with default values of 100 for Resources.WOOD and 50 for Resources.STONE.
     * The inventory can be updated through the trade() method.
     *
     * @see Trader#trade(Castle, Resources, int)
     */
    private Map<Resources, Integer> inventory;

    /**
     * Trader class represents a non-player character in the game that trades with castles.
     * Trader extends the NPC class.
     */
    public Trader(int range) {
        super(range,1_000_000, 1_000_000, 1_000_000);
        random = new Random();
        initializePrices();
        initializeInventory();
    }

    /**
     * Initializes the prices of resources for the Trader.
     * This method is called during the construction of the Trader object to set the initial prices of each resource.
     * The prices are stored in a HashMap, with the resource as the key and the price as the value.
     * The following resources are initialized with their respective prices:
     * - WOOD: 1
     * - STONE: 2
     */
    private void initializePrices() {
        prices = new HashMap<>();
        prices.put(Resources.WOOD, 1);
        prices.put(Resources.STONE, 2);
    }

    /**
     * Initializes the inventory for the Trader.
     * The inventory is represented by a map that stores the resources and their quantities.
     * This method adds initial resources and quantities to the inventory map.
     *
     * @see Trader#inventory
     * @see Trader#initializePrices()
     * @see Trader#initializeInventory()
     */
    private void initializeInventory() {
        inventory = new HashMap<>();
        inventory.put(Resources.WOOD, 100);
        inventory.put(Resources.STONE, 50);
    }

    @Override
    public void idleAction(Castle castle) {
        getNeighbouringHeroes(castle);
        giveOutResources(castle);
    }

    @Override
    public void attack(Unit target) {
        target.kill();
    }

    /**
     * This method is used by the Trader class to give out resources to neighbouring Heroes that belong to a specific Castle.
     * The resources given out are determined randomly based on a probability distribution. The method will adjust the resource
     * amount in the Castle based on the amount of the resource specified by the given probability distribution.
     *
     * @param castle the Castle object to give resources to the neighbouring Heroes
     */
    @Override
    public void giveOutResources(Castle castle) {
        double i=random.nextDouble();
        int amount=0;
        Resources r=null;
        if(i>=0.99){
            r=Resources.AMBER;
            amount =1;
        } else if(i>=0.98){
            r=Resources.HEART_OF_THE_SEA;
            amount =1;
        } else if(i>=0.90){
            r=Resources.GOLD;
            amount=15;
        }else if(i>=0.80){
            r=Resources.HERBS;
            amount=5;
        }
        else if(i>=0.65){
            r=Resources.WOOD;
            amount=10;
        }
        else if(i>=0.50){
            r=Resources.STONE;
            amount=10;
        }
        for(Hero hero : neighbouringHeroes){
            if (hero.getOwner().equals(castle)){
                castle.adjustResource(r, amount);
            }
        }
    }
    /**
     * Retrieves the price of a given resource.
     *
     * @param resource the resource to get the price of
     * @return the price of the given resource
     */
    public int getPrice(Resources resource){
        return prices.get(resource);
    }

    /**
     * Performs a trade between a castle and a trader.
     * This method allows the castle to buy a specified quantity of a resource from the trader.
     *
     * @param castle   The castle involved in the trade.
     * @param resource The resource to be traded.
     * @param quantity The quantity of the resource to be traded.
     * @throws InsufficientResourcesException If the castle does not have enough money
     *                                       to buy the specified quantity of the resource,
     *                                       or if the trader's stock of the resource is insufficient.
     */
    public void trade(Castle castle, Resources resource, int quantity) {
        Integer pricePerUnit = prices.get(resource);
        if (pricePerUnit == null) {
            return;
        }

        int totalPrice = pricePerUnit * quantity;
        int castleMoney = castle.getResourceAmount(Resources.GOLD);
        int resourceStock = inventory.getOrDefault(resource, 0);

        if (castleMoney < totalPrice){
            throw new InsufficientResourcesException("You don't have enough money to buy this item");
        } else if(resourceStock < quantity){
            throw new InsufficientResourcesException("Trader is low on stock on this item");
        }

        castle.adjustResource(Resources.GOLD, -totalPrice);
        castle.adjustResource(resource, quantity);
        inventory.put(resource, resourceStock - quantity);
    }
}
