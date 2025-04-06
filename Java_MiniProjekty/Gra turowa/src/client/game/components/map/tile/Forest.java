package client.game.components.map.tile;

import client.game.components.Resources;
import client.game.components.unit.npc.Thief;
import client.game.components.utils.BiomeProbabilites;
import client.game.components.Castle;

import java.util.Random;

public class Forest extends Tile {
    private static final Random random = new Random();
    /**
     * Minimum range for the Thief unit's abilities.
     */
    private static final int MIN_THIEF_RANGE = 1;
    /**
     * The maximum range for Thief units.
     * This variable is used to determine the maximum range a Thief unit can have.
     */
    private static final int MAX_THIEF_RANGE = 4;
    /**
     *
     */
    private final BiomeProbabilites biome;
    private final Resources resource;
    /**
     * Constructs a new Forest instance with the specified coordinates.
     *
     * @param x The x coordinate of the forest tile.
     * @param y The y coordinate of the forest tile.
     */
    public Forest(int x, int y){
        super(x,y);
        this.movementCost=2;
        this.biome = BiomeProbabilites.FOREST;
        this.resource = biome.getResource();
        spawnThieves();
    }

    /**
     * Spawns thieves on the forest tile.
     */
    private void spawnThieves() {
        if (shouldSpawnThief()) {
            Thief thief = createThief();
            setUnitOnTile(thief);
            thief.setTile(this);
        }
    }

    /**
     * Determines whether a Thief should be spawned in a Forest tile.
     *
     * @return true if a Thief should be spawned, false otherwise.
     */
    private boolean shouldSpawnThief() {
        return random.nextDouble() <= biome.getSpawnProbability();
    }

    /**
     * Creates a new Thief for the Forest tile. The Thief's range is generated randomly between MIN_THIEF_RANGE and MAX_THIEF_RANGE.
     * The newly created Thief is then assigned to the Forest tile.
     *
     * @return The created Thief.
     */
    private Thief createThief() {
        int range = random.nextInt(MIN_THIEF_RANGE, MAX_THIEF_RANGE);
        Thief thief = new Thief(range);
        thief.setTile(this);
        return thief;
    }
    @Override
    public String toString() {
        return "F";
    }
    @Override
    public void returnResource(Castle castle) {
        if (random.nextDouble() <= biome.getMintProbability()){
            castle.adjustResource(biome.getResource(), random.nextInt(10,15));
        }
    }

    @Override
    public Resources getResource() {
        return resource;
    }
}
