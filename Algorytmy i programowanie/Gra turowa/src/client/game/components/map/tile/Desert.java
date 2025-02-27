package client.game.components.map.tile;

import client.game.components.Resources;
import client.game.components.unit.npc.Trader;
import client.game.components.utils.BiomeProbabilites;
import client.game.components.Castle;

import java.util.Random;

public class Desert extends Tile {
    private static final Random random = new Random();
    /**
     *
     */
    private static final int MIN_TRADER_RANGE = 1;
    /**
     * The maximum range in which a trader can spawn on a Desert tile.
     *
     * It is used in the Desert class to determine the range of the traders created.
     * The range is a randomly generated number between MIN_TRADER_RANGE (inclusive) and MAX_TRADER_RANGE (exclusive).
     *
     * The value of MAX_TRADER_RANGE is set to 5.
     *
     * @see Desert
     */
    private static final int MAX_TRADER_RANGE = 5;
    /**
     * Represents a private final variable holding the biome probabilities.
     */
    private final BiomeProbabilites biome;
    private final Resources resource;

    /**
     * Represents a desert tile on the map.
     * Inherits from the Tile class.
     */
    public Desert(int x, int y) {
        super(x, y);
        this.movementCost = 2;
        this.biome = BiomeProbabilites.DESERT;
        this.resource = biome.getResource();
        spawnTraders();
    }

    /**
     * Spawns a Trader on the current Desert Tile based on the spawn probability defined by the biome.
     * If a Trader is spawned, it is created and set on the tile using the setUnitOnTile() method.
     */
    private void spawnTraders() {
        if (shouldSpawnTrader()) {
            Trader trader = createTrader();
            setUnitOnTile(trader);
            trader.setTile(this);
        }
    }

    /**
     * Checks if a trader should be spawned on the current tile based on the biome's spawn probability.
     *
     * @return true if a trader should be spawned, false otherwise
     */
    private boolean shouldSpawnTrader() {
        return random.nextDouble() <= biome.getSpawnProbability();
    }

    /**
     * Creates a new Trader object with a random trading range and sets the current tile as its tile.
     *
     * @return the created Trader object.
     */
    private Trader createTrader() {
        int range = random.nextInt(MIN_TRADER_RANGE, MAX_TRADER_RANGE);
        Trader trader = new Trader(range);
        trader.setTile(this);
        return trader;
    }

    @Override
    public String toString() {
        return "D";
    }

    @Override
    public void returnResource(Castle castle) {
        if (random.nextDouble() <= biome.getMintProbability()){
            castle.adjustResource(biome.getResource(), random.nextInt(1,2));
        }
    }

    @Override
    public Resources getResource() {
        return resource;
    }
}
