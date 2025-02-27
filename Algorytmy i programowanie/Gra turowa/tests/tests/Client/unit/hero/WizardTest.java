package tests.Client.unit.hero;
import client.game.components.map.WorldMap;
import client.game.components.map.tile.Tile;
import client.game.components.map.tile.Water;
import client.game.components.unit.hero.Hero;
import client.game.components.unit.hero.Wizard;
import client.game.components.unit.npc.Barbarian;
import client.game.components.unit.npc.NPC;
import client.game.components.Castle;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class WizardTest {
    private Barbarian barbarian;
    private Barbarian barbarian2;
    private Hero wizard;
    private Castle castle;
    private WorldMap map;


    @Before
    public void setup() {
        map = new WorldMap(100);
        barbarian = new Barbarian(8, NPC.Level.EASY);
        castle = new Castle(1,1);
        wizard = new Wizard(castle);
        barbarian2 = new Barbarian(8, NPC.Level.EASY);
        map.placeUnitOnMap(barbarian, 15, 15);
        map.placeUnitOnMap(wizard, 15,14);
        map.placeUnitOnMap(barbarian2, 15,12);
    }
    @Test
    public void testAction() {
        System.out.println("wizard health: "+wizard.getHealth());
        System.out.println("barb1 health "+barbarian.getHealth());
        System.out.println(barbarian.isAlive());
        System.out.println(map.getTile(15,13).getUnitOnTile());
        System.out.println(map.getTile(15,13).toString());
        System.out.println(map.getTile(15,15).getUnitOnTile());
        wizard.doAction(Wizard->{
            wizard.attack(barbarian);
            wizard.attack(barbarian);
            return null;
        });
        wizard.setStatsForNewTurn();
        System.out.println(barbarian.getHealth());
        System.out.println(barbarian.isAlive());
        System.out.println(wizard.getTile().getCoordinates()[0]+" "+wizard.getTile().getCoordinates()[1]);
        wizard.doAction(Wizard->{
            wizard.walk(map.getTile(15,13));
            return null;
        });
        barbarian2.idleAction(castle);
        barbarian2.idleAction(castle);
        System.out.println(wizard.getTile().getCoordinates()[0]+" "+wizard.getTile().getCoordinates()[1]);
        System.out.println("wizard health: "+wizard.getHealth());
        System.out.println("barb2 health: "+barbarian2.getHealth());
        wizard.doAction(Wizard->{
            wizard.specialAction(barbarian2);
            return null;
        });
        System.out.println(wizard.getTile().getCoordinates()[0]+" "+wizard.getTile().getCoordinates()[1]);
        System.out.println("wizard health: "+wizard.getHealth());
        System.out.println("wizard max health: "+wizard.getMaxHealth());
        System.out.println("barb2 health: "+barbarian2.getHealth());
    }
    @Test
    public void testWalk(){
        Water water = new Water(3,3);
        assertTrue(wizard.walk(water));
    }
}
