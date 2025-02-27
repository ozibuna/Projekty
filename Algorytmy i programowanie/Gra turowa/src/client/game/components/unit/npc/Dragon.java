package client.game.components.unit.npc;

import client.game.components.unit.Unit;
import client.game.components.Castle;
import client.game.components.Resources;

public class Dragon extends NPC {

    public Dragon(int range) {
        super(range, 80, 1500, 20);
    }

    @Override
    public void idleAction(Castle castle) {
        selfHeal();
    }

    @Override
    public void attack(Unit target) {
        int amplifier = random.nextInt(40) - 20; // Random amplifier for damage
        if (target.isAlive()) {
            target.setHealth(target.getHealth() - (damage + amplifier) - target.getDefense());
            if (target.getHealth() <= 0) {
                target.kill();
            }
        }
    }

    private void selfHeal() {
        if (health < maxHealth) {
            int healChance = random.nextInt(10); // 1 out of 10 chance not to heal (10% chance)
            if (healChance != 0 || healChance != 1) { // 80% chance to heal
                int healAmount = (int) (maxHealth * 0.1 * random.nextDouble()); // Heal up to 10% of max health
                health = Math.min(health + healAmount, maxHealth); // Ensure health does not exceed max health
            }
        }
    }

    @Override
    public void giveOutResources(Castle castle) {
        castle.adjustResource(Resources.GOLD, calculateResources());
    }

    private int calculateResources() {
        return random.nextInt(250, 500);
    }
}
