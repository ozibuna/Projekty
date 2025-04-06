package client.game.controller;

import client.game.components.Castle;
import client.game.components.map.WorldMap;
import client.game.components.map.tile.Tile;
import client.game.components.unit.Unit;

import java.util.List;

public class MapController {
    public MapController(){

    }
    public Tile getTile(WorldMap map, int x, int y){
        return map.getTile(x, y);
    }
    public List<Castle> getCastles(WorldMap map){
        return map.getCastles();
    }
    public void placeUnit(Unit unit, Tile tile){
        try{
            tile.setUnitOnTile(unit);
            unit.setTile(tile);
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
