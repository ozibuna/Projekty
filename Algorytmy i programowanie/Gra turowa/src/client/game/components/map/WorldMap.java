package client.game.components.map;

import client.game.components.map.tile.Tile;
import client.game.components.unit.Unit;
import client.game.components.unit.npc.NPC;
import client.game.components.utils.Directions;
import client.game.components.Castle;
import client.game.components.utils.exceptions.TileAlreadyOccupiedException;
import client.game.components.utils.GameUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class WorldMap implements Serializable{
    /**
     * A two-dimensional array of tiles representing the map.
     * <p>
     * Each element of the array represents a single tile on the map
     * and holds its coordinates, terrain and unit (if present).
     */
    public Tile[][] tiles;
    /**
     * The variable "castles" is a list of Castle objects. It is a field in the WorldMap class.
     * <p>
     * The WorldMap class represents a map in a game. It contains tiles, castles, and non-player characters (NPCs).
     */
    public List<Castle> castles;
    /**
     * This class represents a list of non-player characters (NPCs).
     * NPCs are characters in the game that are controlled by the computer.
     */
    public List<NPC> npcs;
    /**
     * Represents the size of something.
     */
    private int size;

    /**
     * Represents a world map with tiles, castles, and NPCs.
     */
    public WorldMap(int size, int playerNumber){
        MapGenerator generator = new MapGenerator();
        this.tiles = generator.generateMap(size, playerNumber);
        this.castles = generator.castles();
        this.size = size;
        this.npcs = new ArrayList<>();
        getAllNPCs();
        setAllNPCsMap();

    }

    /**
     * Represents a world map with tiles and castles.
     */
    public WorldMap(int size, Tile[][] tiles, List<Castle> castles){
        this.size = size;
        this.tiles = tiles;
        this.castles = castles;
    }

    /**
     * Retrieves all non-player character (NPC) units from the tiles array and adds them to the npcs list.
     */
    public void getAllNPCs(){
        Arrays.stream(tiles).forEach(row -> Arrays.stream(row)
                .filter(el -> el.getUnitOnTile() instanceof NPC).forEach(el -> addNPC((NPC)el.getUnitOnTile())));
    }

    public void setAllNPCsMap(){
        npcs.forEach(npc -> npc.setMap(this));
    }
    /**
     * Adds an NPC (Non-Player Character) to the world map. NPCs are characters controlled by the computer.
     * This method adds the provided NPC to the list of NPCs in the world map.
     *
     * @param npc the NPC to be added to the world map
     */
    public void addNPC(NPC npc){
        npcs.add(npc);
    }
    /**
     * Deletes an NPC from the list of NPCs when it is killed.
     *
     * @param npc the NPC to delete
     */
    // TODO delete NPC from the list when it is killed
    public void deleteNPC(NPC npc){
        npcs.remove(npc);
    }
    /**
     * Sets the tiles of the world map.
     *
     * @param tiles the 2D array of tiles to be set
     */
    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    /**
     * Sets the list of castles on the world map.
     *
     * @param castles the list of castles to be set on the world map
     */
    public void setCastles(List<Castle> castles) {
        this.castles = castles;
    }

    /**
     * Sets the size of the WorldMap.
     *
     * @param size the new size of the WorldMap
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Retrieves the tiles of the world map.
     *
     * @return The tiles of the world map.
     */
    public Tile[][] getTiles() {
        return tiles;
    }

    /**
     * Retrieves a list of castles in the world map.
     *
     * @return List<Castle> - A list of Castle objects representing all the castles in the world map.
     */
    public List<Castle> getCastles() {
        return castles;
    }

    /**
     * Modifies the terrain of the world map at the specified coordinates
     * by replacing the tile with the given tile object.
     *
     * @param x    the x-coordinate of the tile to modify
     * @param y    the y-coordinate of the tile to modify
     * @param tile the new tile to replace the existing tile with
     * @param <T>  the type of the tile
     */
    public <T extends Tile> void modifyTerrain(int x, int y, T tile){
        tiles[x][y] = tile;
    }
    /**
     * Retrieves the tile at the specified coordinates.
     *
     * @param x the x-coordinate of the tile
     * @param y the y-coordinate of the tile
     * @return the tile at the given coordinates
     */
    public Tile getTile(int x, int y){
        return tiles[x][y];
    }
    /**
     * Retrieves the size of the WorldMap.
     *
     * @return The size of the WorldMap.
     */
    public int getSize(){
        return size;
    }
    /**
     * Generates a map with the given size.
     *
     * @param size the size of the map to generate
     */
    public void generateMap(int size){
        MapGenerator generator = new MapGenerator();
        //tiles = generator.generateMap(size);
    }

    /**
     * Retrieves the neighboring tiles around the specified coordinates (x, y).
     *
     * @param x the x-coordinate of the center tile
     * @param y the y-coordinate of the center tile
     * @return an array of neighboring tiles, with null values for invalid coordinates
     */
    public Tile[] getNeighboringTiles(int x, int y) {
        Tile[] neighbors = new Tile[4];
        Directions[] directions = Directions.values();
        for (int i = 0; i < directions.length; i++) {
            int nx = x + GameUtils.getDirection(directions[i])[0];
            int ny = y + GameUtils.getDirection(directions[i])[1];
            if (GameUtils.areCoordinatesValid(nx,ny,size)) {
                neighbors[i] = tiles[nx][ny];
            } else {
                neighbors[i] = null;
            }
        }
        return neighbors;
    }
    /**
     * Places a unit on the map at the specified coordinates.
     *
     * @param unit the unit to be placed on the map
     * @param x the x-coordinate of the tile to place the unit on
     * @param y the y-coordinate of the tile to place the unit on
     * @throws TileAlreadyOccupiedException if the tile at the specified coordinates is already occupied
     */
    public void placeUnitOnMap(Unit unit, int x, int y){
        if (tiles[x][y].isOccupied()){
            throw new TileAlreadyOccupiedException("This tile already has a unit");
        }
        tiles[x][y].setUnitOnTile(unit);
        unit.setTile(tiles[x][y]);
        unit.setMap(this);
    }

    public int getRemainingCastles(){
        return (int) castles.stream().filter(castle -> !castle.isDestroyed()).count();
    }
    /**
     * Runs the actions of all non-player characters (NPCs) in the given castle.
     *
     * @param castle the castle in which the NPCs are located
     */
    public void runNPCActions(Castle castle){
        npcs.forEach(NPC -> NPC.idleAction(castle));
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        WorldMap worldMap = (WorldMap) object;
        return size == worldMap.size && Objects.deepEquals(tiles, worldMap.tiles) && Objects.equals(castles, worldMap.castles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.deepHashCode(tiles), castles, size);
    }

    public boolean isValidTile(int x, int y) {
        if (x >= 0 && x < size && y >= 0 && y < size)
            return true;
        else
            return false;
    }
}
