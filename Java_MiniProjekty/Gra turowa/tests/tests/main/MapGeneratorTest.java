package tests.main;

import client.game.components.Castle;
import client.game.components.map.MapGenerator;
import client.game.components.map.tile.Tile;
import client.game.components.map.tile.Village;
import client.game.components.unit.npc.Dragon;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class MapGeneratorTest {
    Tile[][] worldMap;
    MapGenerator generator = new MapGenerator();
    @Before
    public void mapInit() {
        this.worldMap = generator.generateMap(400);
    }
    @Test
    public void testVillage() {
        int EXPECTED_VILLAGE_QUANTITY = 50;
        int villagesQuantity = 0;
        for (Tile[] tile : worldMap) {
            for (Tile t : tile) {
                if (t instanceof Village) {
                    villagesQuantity++;
                }
            }
        }
        Assert.assertEquals(EXPECTED_VILLAGE_QUANTITY, villagesQuantity);
    }

    @Test
    public void testAreAllBiomesPreset() {
        int BIOMES_QUANTITY = 7;
        List<Class<? extends Tile>> biomes = new ArrayList<>();
        for (Tile[] tile : worldMap) {
            for (Tile t : tile) {
                if (!biomes.contains(t.getClass())) {
                    biomes.addLast(t.getClass());
                }
            }
        }
        System.out.println(biomes);
        Assert.assertEquals(BIOMES_QUANTITY, biomes.size());
    }

    @Test
    public void testAreAllCastlesPresent() {
        int castlesQuantity = 0;
        for (Tile[] tile : worldMap) {
            for (Tile t : tile) {
                if (t instanceof Castle) {
                    castlesQuantity++;
                }
            }
        }
        Assertions.assertEquals(4, castlesQuantity);
        Assertions.assertEquals(4, castlesQuantity);
    }
    @Test
    public void testCastlesListLength() {
        Assertions.assertTrue(generator.castles().size() == 4);
    }
    @Test
    public void isDragonPresent() {
        boolean isDragonPresent = false;
        for (Tile[] tile : worldMap) {
            for (Tile t : tile) {
                if (t.getUnitOnTile() instanceof Dragon) {
                    isDragonPresent = true;
                }
            }
        }
        Assertions.assertTrue(isDragonPresent);
    }
}
