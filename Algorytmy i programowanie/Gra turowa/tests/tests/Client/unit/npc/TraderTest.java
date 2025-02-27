package tests.Client.unit.npc;

import client.game.components.map.WorldMap;
import client.game.components.unit.hero.Hero;
import client.game.components.unit.hero.Knight;
import client.game.components.Castle;
import client.game.components.Resources;
import client.game.components.unit.npc.Trader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TraderTest {
    Trader trader;
    Castle castle;
    Hero knight;
    WorldMap map;
    @BeforeEach
    public void setup(){
        map = new WorldMap(100);
        trader = new Trader(10);
        map.placeUnitOnMap(trader, 10, 10);
        castle = map.getCastles().get(1);
        knight = new Knight(castle);
        map.placeUnitOnMap(knight, 10,11);
    }

    @Test
    public void trade() {
        castle.adjustResource(Resources.GOLD, 1000);
        trader.trade(castle, Resources.WOOD, 100);
        assertEquals(100, castle.getResourceAmount(Resources.WOOD));
        assertEquals(900, castle.getResourceAmount(Resources.GOLD));
    }
    @Test
    public void idleTest(){
        for(int i=0;i<10000;i++){
            trader.idleAction(castle);
        }
        for (Resources resource : Resources.values()){
            System.out.println(resource + ": " + castle.getResourceAmount(resource));
        }
    }
}