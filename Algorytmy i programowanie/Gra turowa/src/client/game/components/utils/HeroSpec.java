package client.game.components.utils;

import client.game.components.unit.hero.Hero;
import client.game.components.Resources;


import java.util.Map;

public class HeroSpec {
    /**
     *
     */
    private final Class<? extends Hero> heroClass;
    /**
     * The resource requirements for a hero.
     *
     * This map contains the resource requirements for a specific hero. The key represents
     * a resource type, and the value represents the amount of that resource required.
     *
     * @see HeroSpec
     * @see Resources
     */
    private final Map<Resources, Integer> resourceRequirements;

    /**
     * The HeroSpec class represents a specification for a hero. It contains information about the hero's class and the resource requirements needed to create the hero.
     */
    public HeroSpec(Class<? extends Hero> heroClass, Map<Resources, Integer> resourceRequirements) {
        this.heroClass = heroClass;
        this.resourceRequirements = resourceRequirements;
    }

    /**
     * Retrieves the class of the hero associated with this HeroSpec.
     *
     * @return The class of the hero.
     */
    public Class<? extends Hero> getHeroClass() {
        return heroClass;
    }

    /**
     * Returns the resource requirements for spawning a hero.
     *
     * @return A map of the required resources and their corresponding amounts.
     */
    public Map<Resources, Integer> getResourceRequirements() {
        return resourceRequirements;
    }
}

