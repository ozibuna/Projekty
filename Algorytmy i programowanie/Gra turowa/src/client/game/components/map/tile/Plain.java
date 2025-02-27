package client.game.components.map.tile;

import client.game.components.Resources;
import client.game.components.utils.BiomeProbabilites;
import client.game.components.Castle;

import java.util.Random;

public class Plain extends Tile{
    /**
     * Represents the probabilities associated with a specific biome.
     */
    private final BiomeProbabilites biome;
    private final Resources resource;
    /**
     * A single tile of the map, holds its coordinates, terrain and unit (if present)
     */
    //TODO make something with these ugly randoms
    private final Random random = new Random();
    /**
     * Initializes a Plain tile with the given coordinates.
     * The Plain tile is a subclass of the Tile class and represents a plain biome on the map.
     * It sets the biome to Plain, movement cost to 1, and x and y coordinates to the given values.
     *
     * @param x the x-coordinate of the tile
     * @param y the y-coordinate of the tile
     */
    public Plain(int x, int y){
        super(x, y);
        this.biome = BiomeProbabilites.PLAIN;
        this.resource = biome.getResource();
        this.movementCost=1;
    }

    @Override
    public String toString() {
        return "P";
    }
    @Override
    public void returnResource(Castle castle) {
        if (random.nextDouble() <= biome.getMintProbability()){
            castle.adjustResource(biome.getResource(), random.nextInt(20,50));
        }
    }

    @Override
    public Resources getResource() {
        return resource;
    }
}
