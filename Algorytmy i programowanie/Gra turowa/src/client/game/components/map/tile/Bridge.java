package client.game.components.map.tile;

import client.game.components.Resources;
import client.game.components.utils.BiomeProbabilites;
import client.game.components.Castle;

import java.util.Random;

public class Bridge extends Tile{
    /**
     * This constant represents the amount of wood required to build a bridge.
     * The value is set to 200.
     */
    public static final int WOOD_FOR_BRIDGE = 200;
    /**
     * The amount of stone required to build a bridge.
     */
    public static final int STONE_FOR_BRIDGE = 200;
    /**
     * Represents the probabilities of a biome. Used as a private final variable.
     */
    private final BiomeProbabilites biome;
    private final  Resources resource;
    /**
     * The random variable is an instance of the Random class.
     * It is used to generate random numbers.
     * This can be used for various purposes, such as randomizing game elements or generating random data.
     */
    private final Random random = new Random();

    /**
     * Represents a bridge tile on the map.
     */
    public Bridge(int x, int y) {
        super(x, y);
        this.movementCost = 1;
        this.biome = BiomeProbabilites.BRIDGE;
        this.resource = biome.getResource();
    }

    @Override
    public void returnResource(Castle castle) {
        if (random.nextDouble() <= biome.getMintProbability()){
            castle.adjustResource(biome.getResource(), 1);
        }
    }

    @Override
    public Resources getResource() {
        return resource;
    }
}
