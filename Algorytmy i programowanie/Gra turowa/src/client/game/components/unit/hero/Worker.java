package client.game.components.unit.hero;

import client.game.components.map.WorldMap;
import client.game.components.map.tile.Tile;
import client.game.components.map.tile.Water;
import client.game.components.unit.Unit;
import client.game.components.map.tile.Bridge;
import client.game.components.utils.Directions;
import client.game.components.utils.GameUtils;
import client.game.components.utils.exceptions.InsufficientResourcesException;
import client.game.components.Castle;
import client.game.components.Resources;

import java.util.ArrayList;

public class Worker extends Hero{

    /**
     * A class representing a worker unit in the game.
     */
    public Worker(Castle owner){
        super(owner,10,100,5);
    }

    @Override
    public void specialAction(Unit target) {
        if(movementPoints<=2) {
            return;
        }
        movementPoints-=2;
        this.tile.returnResource(owner);
    }

    @Override
    public boolean walk(Tile destination){
        if(super.walk(destination)){
            destination.returnResource(owner);
            return true;
        }
        return false;
    }

    @Override
    public void attack(Unit target) {
        if(target instanceof Hero){
            if (this.isSameOwner(((Hero) target).getOwner())){
                throw new InvalidTargetException("You can't attack your own troops moron");
            }
        }
        if(movementPoints<=2) {
            return;
        }
        movementPoints-=2;
        target.setHealth(target.getHealth() - damage);
        if (target.getHealth() <= 0) {
            target.kill();
        }
    }
    /**
     * Returns the directions of neighboring river tiles to the current tile associated with this unit.
     *
     * @return ArrayList of Directions representing the directions of neighboring river tiles.
     */
    public ArrayList<Directions> getRiverDirections() {
        int[] coordinates = getCoordinates();
        Tile[] neighbours = map.getNeighboringTiles(coordinates[0], coordinates[1]);
        ArrayList<Directions> directions = new ArrayList<>();
        int i = 0;
        for (Tile tile : neighbours) {
            if (tile instanceof Water) {
                switch (i) {
                    case 0: directions.add(Directions.UP); break;
                    case 1: directions.add(Directions.DOWN); break;
                    case 2: directions.add(Directions.LEFT); break;
                    case 3: directions.add(Directions.RIGHT); break;
                }
            }
            i++;
        }
        return directions;
    }
    /**
     * Builds a bridge on the world map in the specified direction.
     *
     * @param map               the world map on which the bridge is to be built
     * @param buildingDirection the direction in which the bridge is to be built
     * @throws InsufficientResourcesException if the player does not have enough resources to build the bridge
     */
    public void buildBridge(WorldMap map, Directions buildingDirection) {
        if(movementPoints<=3) {
            return;
        }
        movementPoints-=3;
        int[] coordinates = getCoordinates();
        // check if resources are sufficient to build a bridge
        if (owner.getResourceAmount(Resources.WOOD) < Bridge.WOOD_FOR_BRIDGE
                || owner.getResourceAmount(Resources.STONE) < Bridge.STONE_FOR_BRIDGE) {
            throw new InsufficientResourcesException("You don't have enough resources to build the bridge.");
        }

        owner.adjustResource(Resources.WOOD, -Bridge.WOOD_FOR_BRIDGE);
        owner.adjustResource(Resources.STONE, -Bridge.STONE_FOR_BRIDGE);

        int riverIndexX = coordinates[0] + GameUtils.getDirection(buildingDirection)[0];
        int riverIndexY = coordinates[1] + GameUtils.getDirection(buildingDirection)[1];

        while (GameUtils.areCoordinatesValid(riverIndexX, riverIndexY, map.getSize())
                && map.getTile(riverIndexX, riverIndexY) instanceof Water) {
            map.modifyTerrain(riverIndexX, riverIndexY, new Bridge(riverIndexX, riverIndexY));
            riverIndexX += GameUtils.getDirection(buildingDirection)[0];
            riverIndexY += GameUtils.getDirection(buildingDirection)[1];
        }
    }
    @Override
    public Worker clone() throws CloneNotSupportedException {
        return (Worker) super.clone();
    }
}
