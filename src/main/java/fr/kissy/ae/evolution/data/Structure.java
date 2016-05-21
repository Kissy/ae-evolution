package fr.kissy.ae.evolution.data;

public enum Structure {
    URBAN_STRUCTURES(StructureType.SUPPORT, 1, 0, 0, 0, -1, 0),
    SOLAR_PLANTS(StructureType.SUPPORT, 1, 0, 0, -1, -1, 0),
    GAS_PLANTS(StructureType.SUPPORT, 1, 0, 0, -1, -1, 0),
    FUSION_PLANTS(StructureType.SUPPORT, 20, 4, 0, -1, -1, 0),
    ANTIMATTER_PLANTS(StructureType.SUPPORT, 2000, 10, 0, -1, -1, 0),
    ORBITAL_PLANTS(StructureType.SUPPORT, 40000, 12, 0, -1, 0, 0),
    RESEARCH_LABS(StructureType.FACILITIES, 2, -1, 0, -1, -1, 0),
    METAL_REFINERIES(StructureType.FACILITIES, 1, -1, 1, -1, -1, 0),
    CRYSTAL_MINES(StructureType.SUPPORT, 2, -1, 0, -1, -1, 0),
    ROBOTIC_FACTORIES(StructureType.FACILITIES, 5, -1, 0, -1, -1, 2),
    SHIPYARDS(StructureType.FACILITIES, 5, -1, 1, -1, -1, 0),
    ORBITAL_SHIPYARDS(StructureType.FACILITIES, 10000, -12, 2, -1, 0, 0),
    SPACEPORTS(StructureType.FACILITIES, 5, -1, 2, -1, -1, 0),
    COMMAND_CENTERS(StructureType.FACILITIES, 20, -1, 0, -1, -1, 0),
    NANITE_FACTORIES(StructureType.FACILITIES, 80, -2, 2, -1, -1, 4),
    ANDROID_FACTORIES(StructureType.FACILITIES, 1000, -4, 2, -1, -1, 6),
    ECONOMIC_CENTERS(StructureType.FACILITIES, 80, -2, 4, -1, -1, 0),
    TERRAFORM(StructureType.SUPPORT, 80, 0, 0, 0, 5, 0),
    MULTI_LEVEL_PLATFORMS(StructureType.SUPPORT, 10000, 0, 0, 0, 10, 0),
    ORBITAL_BASE(StructureType.SUPPORT, 2000, 0, 0, 10, 0, 0),
    JUMP_GATE(StructureType.FACILITIES, 5000, -8, 0, -1, 0, 0),
    BIOSPHERE_MODIFICATION(StructureType.SUPPORT, 20000, -24, 0, -1, -1, 0),
    CAPITAL(StructureType.FACILITIES, 15000, -12, 10, -1, -1, 0),

    BARRACKS(StructureType.DEFENSES, 5, 0, 0, 0, -1, 0),
    LASER_TURRETS(StructureType.DEFENSES, 10, -1, 0, 0, -1, 0),
    MISSILE_TURRETS(StructureType.DEFENSES, 20, -1, 0, 0, -1, 0),
    PLASMA_TURRETS(StructureType.DEFENSES, 100, -2, 0, 0, -1, 0),
    ION_TURRETS(StructureType.DEFENSES, 256, -3, 0, 0, -1, 0),
    PHOTON_TURRETS(StructureType.DEFENSES, 1024, -4, 0, 0, -1, 0),
    DISRUPTOR_TURRETS(StructureType.DEFENSES, 4096, -8, 0, 0, -1, 0),
    DEFLECTION_SHIELDS(StructureType.DEFENSES, 4096, -8, 0, 0, -1, 0),
    PLANETARY_SHIELD(StructureType.DEFENSES, 25000, -16, 0, 0, -1, 0),
    PLANETARY_RING(StructureType.DEFENSES, 50000, -24, 0, 0, -1, 0);

    private final StructureType type;
    private final Integer credits;
    private final Integer energy;
    private final Integer economy;
    private final Integer population;
    private final Integer area;
    private final Integer capacity;

    Structure(StructureType type, Integer credits, Integer energy, Integer economy, Integer population, Integer area, Integer capacity) {
        this.type = type;
        this.credits = credits;
        this.energy = energy;
        this.economy = economy;
        this.population = population;
        this.area = area;
        this.capacity = capacity;
    }

    public StructureType getType() {
        return type;
    }

    public Integer getCredits() {
        return credits;
    }

    public int getCostForLevel(int level) {
        return (int) Math.ceil(credits * Math.pow(1.5, level - 1));
    }

    public Integer getEnergy() {
        return energy;
    }

    public Integer getEconomy() {
        return economy;
    }

    public Integer getPopulation() {
        return population;
    }

    public Integer getArea() {
        return area;
    }

    public Integer getCapacity() {
        return capacity;
    }
}
