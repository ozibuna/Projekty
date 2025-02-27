package client.game.components.utils;

import client.game.components.Castle;
import client.game.components.Resources;
import client.game.components.unit.hero.*;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class HeroFactory {
    /**
     * A map that stores the specifications of different hero types.
     */
    private final Map<String, HeroSpec> heroSpecifications;

    /**
     * The HeroFactory class is responsible for creating different types of heroes.
     * It contains a map of hero specifications, where each specification is associated
     * with a hero type and the required resources to create that hero. The HeroFactory
     * can spawn a hero of a specific type if the necessary resources are available in
     * the castle.
     */
    public HeroFactory() {
        heroSpecifications = new HashMap<>();
        heroSpecifications.put("Archer", new HeroSpec(
                Archer.class, Map.of(Resources.WOOD, 50, Resources.GOLD, 30)));
        heroSpecifications.put("Knight", new HeroSpec(
                Knight.class, Map.of(Resources.STONE, 80, Resources.GOLD, 20)));
        heroSpecifications.put("Healer", new HeroSpec(
                Healer.class, Map.of(Resources.HERBS, 100, Resources.GOLD, 30)));
        heroSpecifications.put("Druid", new HeroSpec(
                Druid.class, Map.of(Resources.ENCHANTED_CACTUS, 2, Resources.GOLD, 150)));
        heroSpecifications.put("Warlock", new HeroSpec(
                Warlock.class, Map.of(Resources.HEART_OF_THE_SEA, 1, Resources.GOLD, 120)));
        heroSpecifications.put("Wizard", new HeroSpec(
                Wizard.class, Map.of(Resources.AMBER, 2, Resources.GOLD, 100)));
        heroSpecifications.put("Worker", new HeroSpec(
                Worker.class, Map.of(Resources.WOOD, 150, Resources.STONE, 100, Resources.GOLD, 80)));
    }

    /**
     * Spawns a hero of the given type in the specified castle.
     *
     * @param heroType The type of the hero to spawn.
     * @param castle   The castle in which the hero will be spawned.
     * @param <T>      The type of the hero to spawn.
     * @return The spawned hero, or null if the hero cannot be spawned.
     */
    public <T extends Hero> T spawnHero(String heroType, Castle castle) {
        HeroSpec spec = heroSpecifications.get(heroType);
        if (spec != null && canSpawn(spec, castle)) {
            try {
                spec.getResourceRequirements().forEach((resource, amount) ->
                        castle.adjustResource(resource, -amount));
                Constructor<? extends Hero> ctor = spec.getHeroClass().getDeclaredConstructor(Castle.class);
                castle.addTroop(ctor.newInstance(castle));
                return (T) ctor.newInstance(castle);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Checks if a hero can be spawned based on the resource requirements.
     *
     * @param spec   the hero specifications
     * @param castle the castle object
     * @return true if the castle has enough resources to spawn the hero, false otherwise
     */
    private boolean canSpawn(HeroSpec spec, Castle castle) {
        return spec.getResourceRequirements().entrySet().stream()
                .allMatch(entry -> castle.getResourceAmount(entry.getKey()) >= entry.getValue());
    }

}
