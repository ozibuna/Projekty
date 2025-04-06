package client.game.components.utils;

import client.game.components.map.WorldMap;

import java.io.*;

public class MapSaver {
    private WorldMap map;

    public MapSaver(WorldMap mapToSave){
        this.map = mapToSave;
    }

    public void saveGame(){
        try {
            FileOutputStream fileOut = new FileOutputStream("../resources/savedWorldMap.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(map);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in savedWorldMap.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    public WorldMap loadGame(String path){
        WorldMap map = null;
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            map = (WorldMap) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("WorldMap class not found");
            c.printStackTrace();
            return null;
        }
        return map;
    }
}
