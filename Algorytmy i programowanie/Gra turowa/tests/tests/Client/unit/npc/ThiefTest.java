package tests.Client.unit.npc;

import client.game.components.map.WorldMap;
import client.game.components.unit.hero.Archer;
import client.game.components.unit.hero.Druid;
import client.game.components.unit.hero.Hero;
import client.game.components.Castle;
import client.game.components.Resources;
import client.game.components.unit.npc.Thief;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ThiefTest {
    private Thief thief;
    private Castle castle;
    private Hero archer;
    private WorldMap map;


    @Before
    public void setup() {
        map = new WorldMap(100);
        thief = new Thief(8);
        castle = new Castle(1,1);
        archer = new Archer(castle);
        Hero druid  = new Druid(castle);
        map.placeUnitOnMap(thief, 15, 15);
        map.placeUnitOnMap(druid, 11,12);
        map.placeUnitOnMap(archer, 15,14);
    }

    @Test
    public void testIdleAction() {
        castle.adjustResource(Resources.GOLD, 50);
        castle.adjustResource(Resources.WOOD, 50);
        castle.adjustResource(Resources.STONE, 50);
        castle.adjustResource(Resources.ENCHANTED_CACTUS, 50);
        castle.adjustResource(Resources.HEART_OF_THE_SEA, 50);
        castle.adjustResource(Resources.AMBER, 50);
        castle.adjustResource(Resources.HERBS, 50);
        thief.idleAction(castle);
        thief.idleAction(castle);
        for (Resources resources : Resources.values()){
            System.out.println(STR."\{resources} \{castle.getResourceAmount(resources)}");
        }
    }

    @Test
    public void testAttack() {
        archer.setHealth(100);
        thief.attack(archer);
        Assert.assertTrue(archer.getHealth() < 100);
    }

    @Test
    public void testAttackHeroDies() {
        archer.setHealth(1);
        thief.attack(archer);
        Assert.assertFalse(archer.isAlive());
    }

    @Test
    public void testGiveOutResources() {
        int initialGold = castle.getResourceAmount(Resources.GOLD);
        int initialAmber = castle.getResourceAmount(Resources.AMBER);
        thief.giveOutResources(castle);
        Assert.assertTrue(castle.getResourceAmount(Resources.GOLD) > initialGold);
        Assert.assertTrue(castle.getResourceAmount(Resources.AMBER) > initialAmber);
    }
}