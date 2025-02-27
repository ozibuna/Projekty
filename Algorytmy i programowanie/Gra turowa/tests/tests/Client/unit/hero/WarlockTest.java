package tests.Client.unit.hero;

import client.game.components.Castle;
import client.game.components.unit.hero.Druid;
import client.game.components.unit.hero.Hero;
import client.game.components.unit.hero.Warlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * WarlockTest is a class that contains test methods for the functionality of the Warlock class.
 */
public class WarlockTest {
    private Castle castle;
    private Castle castleMockDruid;
    private Warlock warlock;
    public Druid heroMock;

    @BeforeEach
    void init() {

        // Initialize a mock Castle
        castle = new Castle(1, 4);

        // Initialize a Warlock instance
        warlock = new Warlock(castle);

        // Initialize a mock Hero
        heroMock = new Druid(castle);
    }

    @Test
    public void specialAction_success() {
        // Perform the special action
        warlock.specialAction(warlock);

        // Verify that the damage has increased by 5
        assertEquals(75, warlock.getDamage());
    }

    @Test
    public void specialAction_fail_maxDamage() {
        warlock.setDamage(100);
        assertThrows(Hero.InvalidTargetException.class, () -> warlock.specialAction(heroMock));
    }

    @Test
    public void specialAction_selfTarget() {
        assertDoesNotThrow(() -> warlock.specialAction(warlock));
    }
}