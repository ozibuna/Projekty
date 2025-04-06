package client.game.components.unit.npc;


import client.game.components.unit.hero.Hero;
import client.game.components.unit.Unit;
import client.game.components.Castle;
import client.game.components.utils.GameUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class NPC extends Unit {
    /**
     * The `neighbouringHeroes` variable represents a list of nearby heroes for a non-player character (NPC).
     * It is a list of Hero objects that are within the range specified by the NPC's instance variable.
     * The list is populated by the NPC's `getNeighbouringHeroes()` method, which iterates through the surrounding tiles on the map
     * and adds any neighboring heroes belonging to the same castle as the NPC to the list.
     * The `neighbouringHeroes` list is updated whenever the NPC's `getNeighbouringHeroes()` method is called.
     *
     * @see NPC#getNeighbouringHeroes(Castle)
     * @see Hero
     */
    List<Hero> neighbouringHeroes;
    /**
     * This private final variable represents the range of a Non-Playable Character (NPC). The range determines the distance within which the NPC can interact with other units or
     * objects.
     *
     * The initial value of the range variable is set during the construction of an NPC object using the NPC(int range, int damage, int health, int defence) constructor. Once set
     *, the range cannot be modified.
     *
     * @see NPC
     * @see NPC#idleAction(Castle)
     * @see NPC#giveOutResources(Castle)
     * @see NPC#getNeighbouringHeroes(Castle)
     */
    private final int range;
    /**
     * The random variable represents an instance of the Random class.
     * It is used to generate random numbers in the application.
     *
     * @see Random
     */
    protected final Random random = new Random();

    public enum Level {
        EASY, MEDIUM, HARD
    }

    /**
     * Non-Player Character (NPC) class represents a character in the game that is controlled by the computer.
     * NPC extends the Unit class.
     */
    public NPC(int range, int damage, int health, int defence) {
        super(damage, health, defence);
        this.neighbouringHeroes = new ArrayList<>();
        this.range = range;
    }


    /**
     * Perform an idle action for the NPC.
     *
     * @param castle the castle object for performing actions
     */
    public abstract void idleAction(Castle castle);

    /**
     * Gives out resources to a castle.
     * Each subclass of NPC should implement this method to define the specific behavior of giving out resources.
     *
     * @param castle the castle to give resources to
     */
    public abstract void giveOutResources(Castle castle);

    /**
     * Retrieves the neighbouring heroes of the given castle within a certain range.
     *
     * @param castle the castle to retrieve neighbouring heroes from
     */
    public void getNeighbouringHeroes(Castle castle) {
        int[] currentCoordinates = getCoordinates();
        for (int dx = currentCoordinates[0] - range; dx < currentCoordinates[0] + range; dx++) {
            for (int dy = currentCoordinates[1] - range; dy < currentCoordinates[1] + range; dy++) {
                if (GameUtils.areCoordinatesValid(dx, dy, map.getSize())) {
                    Unit unit = map.getTile(dx, dy).getUnitOnTile();
                    if (unit != null && !(unit instanceof NPC) && ((Hero) unit).getOwner().equals(castle)) {
                        this.neighbouringHeroes.add((Hero) unit);
                    }
                }
            }
        }
    }
}
