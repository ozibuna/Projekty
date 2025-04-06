package client.game.components.map.tile;

import client.game.components.Resources;
import client.game.components.unit.npc.Barbarian;
import client.game.components.unit.npc.NPC;
import client.game.components.Castle;

import java.util.Random;

public class Village extends Tile {
    /**
     * Represents whether a village has been plundered or not.
     */
    private boolean isPlundered;
    private final Resources[] resources;
    private boolean canBePlundered;
    /**
     * Represents a Village object that extends the Tile class.
     * Villages are placed on the map and can be plundered by other entities.
     */
    public Village(int x, int y) {
        super(x,y);
        this.movementCost = 3;
        this.resources = new Resources[]{Resources.GOLD,Resources.WOOD,Resources.STONE};
        this.isPlundered = false;
        spawnBarbarian();
    }
    /**
     * Spawns a Barbarian NPC on the tile.
     * The level and range of the spawned Barbarian are randomly determined.
     * The level is chosen from three options: EASY, MEDIUM, HARD.
     * The range is a random value between 1 and 2 for EASY, 1 and 3 for MEDIUM, and 1 and 4 for HARD.
     * The spawned Barbarian is associated with this tile and set as the unit on the tile.
     */
    private void spawnBarbarian() {
        Random random = new Random();
        Barbarian barbarian = null;
        switch (random.nextInt(3)) {
            case 0 -> barbarian = new Barbarian(random.nextInt(1, 2), NPC.Level.EASY);
            case 1 -> barbarian = new Barbarian(random.nextInt(1, 3), NPC.Level.MEDIUM);
            case 2 -> barbarian = new Barbarian(random.nextInt(1, 4), NPC.Level.HARD);
        }
        barbarian.setTile(this);
        this.setUnitOnTile(barbarian);
    }

    public void setCanBePlundered(){
        if (this.getUnitOnTile() == null){
            canBePlundered = true;
        }
    }

    /**
     * Sets the village to a plundered state.
     */
    public void setPlundered() {
        this.isPlundered = true;
    }
    /**
     * Checks if the village has been plundered.
     *
     * @return True if the village has been plundered, false otherwise.
     */
    public boolean isPlundered(){return isPlundered;}
    @Override
    public String toString() {
        return "V";
    }
    @Override
    public void returnResource(Castle castle) {
        castle.adjustResource(Resources.GOLD, 100);
        castle.adjustResource(Resources.WOOD, 200);
        castle.adjustResource(Resources.STONE, 150);
    }

    public Resources[] getResources() {
        return resources;
    }
}
