package tests.main;

import client.game.components.map.WorldMap;
import client.game.components.unit.hero.Worker;
import client.game.components.Castle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WorkerTest {

    private Worker worker;
    private Castle castle;
    private WorldMap map;

    @BeforeEach
    void setUp() {
        castle = new Castle(1,1);
        worker = new Worker(castle);
        map = new WorldMap(100);

    }

    @Test
    void testIsNearRiver() {
        System.setProperty("java.awt.headless", "true");
        map.placeUnitOnMap(worker, 3,4);
        //new MapGUI(map);
    }


}
