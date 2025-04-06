package tests.Client.unit.npc;

import client.game.components.map.WorldMap;
import client.game.components.map.tile.Tile;
import client.game.components.map.tile.Village;
import client.game.components.unit.hero.Archer;
import client.game.components.unit.hero.Hero;
import client.game.components.Castle;
import client.game.components.unit.npc.Barbarian;
import client.game.components.unit.npc.NPC;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BarbarianTest {
    private Barbarian barbarian;
    private Hero archer;
    private Castle castle;
    private WorldMap map;


    @Before
    public void setup() {
        map = new WorldMap(100);
        barbarian = new Barbarian(8, NPC.Level.EASY);
        castle = new Castle(1,1);
        archer = new Archer(castle);
        map.placeUnitOnMap(barbarian, 15, 15);
        map.placeUnitOnMap(archer, 15,14);
    }

    @Test
    public void testIdleAction() {
        barbarian.idleAction(castle);
        System.out.println(archer.getHealth());
    }

    @Test
    public void testPresentInVillages() {
        boolean isBarbarianPresent = true;
        for (Tile[] tile : map.tiles) {
            for (Tile t : tile) {
                if (t instanceof Village) {
                    if (t.getUnitOnTile() == null || !(t.getUnitOnTile() instanceof Barbarian)) {
                        isBarbarianPresent = false;
                    }
                }
            }
        }
        Assert.assertTrue(isBarbarianPresent);
    }
}
