package client.game.components.unit.npc;

import client.game.components.unit.hero.Hero;
import client.game.components.unit.Unit;
import client.game.components.Castle;
import client.game.components.Resources;

public class Thief extends NPC {


    /**
     * The Thief class represents a non-playable character (NPC) in a game. Thieves have the ability to steal
     * resources from neighbouring heroes and attack units. They can also provide resources to a castle.
     */
    public Thief(int range) {
        super(range, 4, 60, 5);
    }

    /**
     * Steals resources from neighbouring castles heroes belong to.
     */
    private void steal() {
        for (Hero hero : neighbouringHeroes) {
            Castle castle = hero.getOwner();
            Resources[] resourceValues = Resources.values();
            Resources randomResource = resourceValues[random.nextInt(resourceValues.length)];
            int amountToSteal = castle.getResourceAmount(randomResource);
            castle.adjustResource(randomResource, -(amountToSteal / random.nextInt(6, 10)));
        }
    }

    @Override
    public void idleAction(Castle castle) {
        getNeighbouringHeroes(castle);
        steal();
    }

    @Override
    public void attack(Unit target) {
        int amplifier = random.nextInt(6) - 2;
        if (target.isAlive()) {
            target.setHealth(target.getHealth() - (damage + amplifier) - target.getDefense());
            if (target.getHealth() <= 0) {
                target.kill();
            }
        }
    }

    @Override
    public void giveOutResources(Castle castle) {
        int goldAmount = random.nextInt(100, 150);
        int amberAmount = random.nextInt(2);
        castle.adjustResource(Resources.GOLD, goldAmount);
        castle.adjustResource(Resources.AMBER, amberAmount);
    }
}
