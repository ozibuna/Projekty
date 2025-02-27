package client.game.components.map.tile;

import client.game.components.Resources;
import client.game.components.utils.BiomeProbabilites;
import client.game.components.Castle;

import java.util.Random;

public class Water extends Tile {
    /**
     * This private instance of the Random class is used to generate random numbers.
     * It is used for determining the probability of returning a resource to a castle.
     */
    private final Random random = new Random();
    /**
     * Represents the biome of a tile.
     *
     * The {@code BiomeProbabilites} enumeration defines different types of biomes that a tile can have.
     * Each biome has associated probabilities for resource spawning and minting.
     *
     * This variable holds the biome type for a specific tile.
     *
     * @see BiomeProbabilites
     * @see Tile
     */
    private final BiomeProbabilites biome;
    private final Resources resource;
    /**
     * Represents a water tile on the map.
     */
    public Water(int x, int y){
        super(x,y);
        this.movementCost=100000;
        this.biome = BiomeProbabilites.WATER;
        this.resource = biome.getResource();
    }
    @Override
    public String toString() {
        return "W";
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
