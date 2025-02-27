package tests.Client.unit.npc;

import client.game.components.Castle;
import client.game.components.map.tile.Tile;
import client.game.components.unit.Unit;
import client.game.components.unit.npc.Dragon;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DragonTest {
    private Unit mockUnit;
    private Dragon dragon;

    @BeforeEach
    public void unitsSetup() {

        mockUnit = new Unit(30, 9, 0) {
            @Override
            public void attack(Unit target) {
                target.setHealth(target.getHealth() - 1000000000);
            }

            @Override
            public void setTile(Tile tile) {
                super.setTile(tile);
            }
        };
        // Create a dragon unit
        dragon = new Dragon(100);
        Tile mockTileUnit = new Tile(1,4) {
            @Override
            public void returnResource(Castle castle) {

            }
        };
        Tile mockTileDragon = new Tile(2,4) {
            @Override
            public void returnResource(Castle castle) {

            }
        };
        mockTileUnit.setUnitOnTile(mockUnit);
        mockUnit.setTile(mockTileUnit);
        dragon.setTile(mockTileDragon);
        mockTileDragon.setUnitOnTile(dragon);
    }

    @Test
    void testDragonAttacksUnit() {
        // Create a mock unit

        int previousHealth = mockUnit.getHealth();

        // Let the dragon attack the mock unit
        dragon.attack(mockUnit);

        // Assert the health of the unit should decrease
        int currentHealth = mockUnit.getHealth();
        assert (previousHealth > currentHealth);

        // If health falls below zero, assert unit is dead
        if (mockUnit.getHealth() <= 0) {
            assertFalse(mockUnit.isAlive());
        }
        System.out.println(STR."Unit health: \{mockUnit.getHealth()}");
    }

    @Test
    void testUnitAttacksDragon() {
        mockUnit.attack(dragon);
        assertTrue(dragon.getHealth() < dragon.getMaxHealth());
        if (dragon.getHealth() <= 0) {
            assertFalse(dragon.getHealth() >= 0);
        }
    }

    @Test
    void testDragonIdleAction() {
        mockUnit.attack(dragon);
        int afterAttack = dragon.getHealth();
        assertTrue(afterAttack < dragon.getMaxHealth());

        dragon.idleAction(null);
        assertTrue(dragon.getHealth() > afterAttack);
        System.out.println(STR."After attack:\{afterAttack} \nAfter heal:  \{dragon.getHealth()}");
    }
}