package client.game.components.map.tile;

import client.game.components.Resources;
import client.game.components.unit.npc.Dragon;
import client.game.components.utils.BiomeProbabilites;
import client.game.components.Castle;

import java.util.Random;

public class Mountains extends Tile {
    /**
     * The biome of a tile, representing the probabilities of resource spawning and minting.
     */
    private final BiomeProbabilites biome;
    private final Resources resource;
    private final Random random = new Random();
    /**
     * Constructs a new Mountains tile at the specified coordinates.
     *
     * @param x The x-coordinate of the tile.
     * @param y The y-coordinate of the tile.
     */
    public Mountains(int x, int y) {
        super(x, y);
        this.biome = BiomeProbabilites.MOUNTAINS;
        this.resource = biome.getResource();
        this.movementCost = 3;

    }
    @Override
    public String toString() {
        if (unitOnTile != null && unitOnTile instanceof Dragon)
            return "S";
        return "M";
    }

    @Override
    public void returnResource(Castle castle) {
        if (random.nextDouble() <= biome.getMintProbability()){
            castle.adjustResource(biome.getResource(), random.nextInt(10,25));
        }
    }

    @Override
    public Resources getResource() {
        return resource;
    }
}
