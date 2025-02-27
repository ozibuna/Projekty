package tests.Client.unit.hero;

import client.game.components.map.WorldMap;
import client.game.components.map.tile.Water;
import client.game.components.unit.hero.Druid;
import client.game.components.unit.hero.Hero;
import client.game.components.unit.hero.Knight;
import client.game.components.unit.npc.Barbarian;
import client.game.components.unit.npc.NPC;
import client.game.components.Castle;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DruidTest {
    private Barbarian barbarian;
    private Barbarian barbarian2;
    private Hero druid;
    private Hero knight;
    private Castle castle;
    private WorldMap map;


    @Before
    public void setup() {
        map = new WorldMap(100);
        barbarian = new Barbarian(8, NPC.Level.EASY);
        castle = new Castle(1,1);
        druid = new Druid(castle);
        knight = new Knight(castle);
        barbarian2 = new Barbarian(8, NPC.Level.EASY);
        barbarian2.setMap(map);
        map.placeUnitOnMap(barbarian, 14, 14);
        map.placeUnitOnMap(barbarian2, 14, 12);
        map.placeUnitOnMap(druid, 15,14);
        map.placeUnitOnMap(knight, 15,13);
    }
    @Test
    public void testAction() {
        druid.doAction(Druid->{
            druid.specialAction(knight);
            return null;
        });
        System.out.println(map.getTile(14,12).getUnitOnTile());
        System.out.println(map.getTile(14,12));
        System.out.println(map.getTile(15,12).getUnitOnTile());
        System.out.println(map.getTile(16,13).getUnitOnTile());
        System.out.println(map.getTile(14,13).getUnitOnTile());
        System.out.println(map.getTile(14,13).getUnitOnTile().getHealth());
        barbarian.idleAction(castle);
        System.out.println("health of original knight "+knight.getHealth());
        System.out.println("health of new knight "+map.getTile(14,13).getUnitOnTile().getHealth());
        Knight k = (Knight) map.getTile(14,13).getUnitOnTile();
        k.doAction(Knight->{
            Knight.attack(barbarian2);
            return null;
        });
        System.out.println(k.getHealth());
        System.out.println(k.getMaxHealth());
        System.out.println("========");
        System.out.println(barbarian2.getHealth());
    }
    @Test
    public void testWalk(){
        Water water = new Water(3,10);
        assertFalse(druid.walk(water));
    }
}
