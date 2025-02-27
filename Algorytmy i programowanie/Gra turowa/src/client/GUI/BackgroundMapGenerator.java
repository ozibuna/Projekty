package client.GUI;

import client.game.components.map.WorldMap;
import client.game.components.map.tile.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BackgroundMapGenerator {
    private final WorldMap worldMap;
    private final int tileSize;
    private BufferedImage[] tileTextures = new BufferedImage[8];

    public BackgroundMapGenerator(WorldMap worldMap, int tileSize) {
        this.worldMap = worldMap;
        this.tileSize = tileSize;
    }

    public BufferedImage generateBackgroundImage(int width, int height) {
        BufferedImage mapImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = mapImage.createGraphics();

        try {
            tileTextures[0] = ImageIO.read(new File("src/textures/desert.png"));
            tileTextures[1] = ImageIO.read(new File("src/textures/forest.png"));
            tileTextures[2] = ImageIO.read(new File("src/textures/mountains.png"));
            tileTextures[3] = ImageIO.read(new File("src/textures/plain.png"));
            tileTextures[4] = ImageIO.read(new File("src/textures/village.png"));
            tileTextures[5] = ImageIO.read(new File("src/textures/water.png"));
            tileTextures[6] = ImageIO.read(new File("src/textures/bridge.png"));
            tileTextures[7] = ImageIO.read(new File("src/textures/castle.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }

        for (int x = 0; x < worldMap.getSize(); x++) {
            for (int y = 0; y < worldMap.getSize(); y++) {
                Tile tile = worldMap.getTile(x, y);
                g2.drawImage(scaleImage(getTileTexture(tile), tileSize, tileSize), y * tileSize, x * tileSize, null);
            }
        }
        g2.dispose();
        return mapImage;
    }

    private static BufferedImage scaleImage(BufferedImage originalImage, int width, int height) {
        Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage bufferedScaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedScaledImage.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();
        return bufferedScaledImage;
    }

    private Color getTileColor(Tile tile) {
        return switch (tile.getClass().getSimpleName()) {
            case "Desert" -> Color.YELLOW;
            case "Forest" -> new Color(0, 102, 0);
            case "Mountains" -> Color.GRAY;
            case "Plain" -> new Color(64, 212, 53);
            case "Village" -> new Color(153, 76, 0);
            default -> Color.green; // to będzie na miejscu rzeki
        };
    }

    private BufferedImage getTileTexture(Tile tile) {
        return switch (tile.getClass().getSimpleName()) {
            case "Desert" -> tileTextures[0];
            case "Forest" -> tileTextures[1];
            case "Mountains" -> tileTextures[2];
            case "Plain" -> tileTextures[3];
            case "Village" -> tileTextures[4];
            case "Water" -> tileTextures[5];
            case "Bridge" -> tileTextures[6];
            default -> tileTextures[7];
        };
    }
}
