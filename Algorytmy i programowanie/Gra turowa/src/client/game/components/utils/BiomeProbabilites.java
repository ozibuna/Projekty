package client.game.components.utils;

import client.game.components.Resources;

public enum BiomeProbabilites {
    FOREST(Resources.WOOD, 0.98, 0.008),
    BRIDGE(Resources.AMBER, 0.1),
    DESERT(Resources.ENCHANTED_CACTUS, 0.08, 0.008),
    MOUNTAINS(Resources.STONE, 0.95),
    PLAIN(Resources.HERBS, 0.98, 0.01),
    WATER(Resources.HEART_OF_THE_SEA, 0.05);
    private final Resources resource;
    private final double mintProbability;
    private double spawnProbability;

    BiomeProbabilites(Resources resource, double mintProbability) {
        this.resource = resource;
        this.mintProbability = mintProbability;
    }
    BiomeProbabilites(Resources resource, double mintProbability, double spawnProbability) {
        this.resource = resource;
        this.mintProbability = mintProbability;
        this.spawnProbability = spawnProbability;
    }
    public Resources getResource() {
        return resource;
    }
    public double getSpawnProbability(){ return spawnProbability; }
    public double getMintProbability() {
        return mintProbability;
    }
}
