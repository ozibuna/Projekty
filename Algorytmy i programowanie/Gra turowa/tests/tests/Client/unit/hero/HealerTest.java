package tests.Client.unit.hero;

import client.game.components.map.WorldMap;
import client.game.components.unit.npc.Barbarian;
import client.game.components.unit.npc.NPC;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import client.game.components.unit.hero.Healer;
import client.game.components.Castle;
import client.game.components.map.tile.Tile;

class HealerTest {
    private Healer healer;
    private Castle owner;

    @BeforeEach
    void setUp() {
        owner = new Castle(2,5);
        healer = new Healer(owner);
        healer.setTile(new Tile(3,5) {
            @Override
            public void returnResource(Castle castle) {

            }
        });
        healer.setMap(new WorldMap(200));
    }

    @Test
    void testAttack() {
        Barbarian target = new Barbarian(2, NPC.Level.MEDIUM);
        Tile targetTile = new Tile(1, 5) {
            @Override
            public void returnResource(Castle castle) {}
        };
        target.setTile(targetTile);

        healer.attack(target);


        Assertions.assertEquals(target.getMaxHealth(), target.getHealth());
    }

    @Test
    void testSpecialAction() {
        Barbarian target = new Barbarian(2, NPC.Level.MEDIUM);
        Tile targetTile = new Tile(5, 5) {
            @Override
            public void returnResource(Castle castle) {}
        };

        target.setTile(targetTile);
        target.setHealth(160);

        healer.specialAction(target);

        Assertions.assertEquals(target.getMaxHealth(), target.getHealth());
    }
}
