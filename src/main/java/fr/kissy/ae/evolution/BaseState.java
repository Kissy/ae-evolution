package fr.kissy.ae.evolution;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import fr.kissy.ae.evolution.action.Action;
import fr.kissy.ae.evolution.data.Structure;
import fr.kissy.ae.evolution.utils.MutableInt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Guillaume Le Biller (<i>guillaume.lebiller@gmail.com</i>)
 * @version $Id$
 */
public class BaseState {
    private int metal;
    private int solar;
    private int gas;
    private int fertility;

    private int economy;
    private int energy;
    private int population;
    private int area;
    private int construction;
    private int production;

    private long duration;
    private int invalidActionCount;
    private boolean satisfyTarget;
    private double fitness;
    private Map<Structure, MutableInt> structureLevels;
    private List<Action> actions;

    private BaseState() {
        this.metal = 3;
        this.solar = 4;
        this.gas = 3;
        this.fertility = 5;

        this.economy = 0;
        this.energy = this.solar;
        this.population = this.fertility;
        this.area = 85;
        this.construction = this.metal;
        this.production = this.metal;

        this.duration = 0;
        this.invalidActionCount = 0;
        this.satisfyTarget = false;
        this.fitness = 0;
    }

    public BaseState(List<Action> actions) {
        this();
        this.structureLevels = Maps.newHashMap();
        for (Structure structure : Structure.values()) {
            this.structureLevels.put(structure, new MutableInt());
        }
        this.actions = actions;
    }

    public BaseState(Map<Structure, Integer> structureLevels) {
        this(Lists.<Action>newArrayList());
        for (Map.Entry<Structure, Integer> entry : structureLevels.entrySet()) {
            this.structureLevels.put(entry.getKey(), new MutableInt(entry.getValue()));
        }
    }

    public int getMetal() {
        return metal;
    }

    public int getSolar() {
        return solar;
    }

    public int getGas() {
        return gas;
    }

    public int getFertility() {
        return fertility;
    }

    public long getDuration() {
        return duration;
    }

    public int getEconomy() {
        return economy;
    }

    public int getEnergy() {
        return energy;
    }

    public int getPopulation() {
        return population;
    }

    public int getArea() {
        return area;
    }

    public int getConstruction() {
        return construction;
    }

    public int getProduction() {
        return production;
    }

    public int getInvalidActionCount() {
        return invalidActionCount;
    }

    public boolean isSatisfyTarget() {
        return satisfyTarget;
    }

    public double getFitness() {
        return fitness;
    }

    public Map<Structure, MutableInt> getStructureLevels() {
        return structureLevels;
    }

    public List<Action> getActions() {
        return actions;
    }

    public boolean canBuild(Structure structure) {
        return this.energy + structure.getEnergy() >= 0 &&
                this.population + structure.getPopulation() >= 0 &&
                this.area + structure.getArea() >= 0;
    }

    public void buildStructure(Structure structure) {
        this.economy += structure.getEconomy();
        this.energy += structure.getEnergy();
        if (structure == Structure.SOLAR_PLANTS) {
            this.energy += this.solar;
        }
        if (structure == Structure.GAS_PLANTS) {
            this.energy += this.gas;
        }
        this.population += structure.getPopulation();
        if (structure == Structure.URBAN_STRUCTURES) {
            this.population += this.fertility;
        }
        this.area += structure.getArea();
        this.construction += structure.getCapacity();
        this.production += structure.getCapacity();
        if (structure == Structure.METAL_REFINERIES) {
            this.construction += this.metal;
            this.production += this.metal;
        }
        this.structureLevels.get(structure).increment();
        int structureCost = structure.getCostForLevel(structureLevels.get(structure).get());
        this.duration += Math.floor(structureCost / ((double) this.construction / 3600));
    }

    public void processActionsUntilSatisfying(HashMap<Structure, Integer> target) {
        for (Action action : actions) {
            if (action.isValid(this)) {
                action.apply(this);
                updateSatisfyTarget(target);
                if (satisfyTarget) {
                    break;
                }
            } else {
                invalidActionCount ++;
            }
        }
    }

    private void updateSatisfyTarget(HashMap<Structure, Integer> target) {
        for (Map.Entry<Structure, Integer> entry : target.entrySet()) {
            if (entry.getValue() > structureLevels.get(entry.getKey()).get()) {
                this.satisfyTarget = false;
                return;
            }
        }
        this.satisfyTarget = true;
    }

    public BaseState copy() {
        return new BaseState(Lists.newArrayList(actions));
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("duration", duration)
                .toString();
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
}
