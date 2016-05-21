package fr.kissy.ae.evolution;

import fr.kissy.ae.evolution.data.Structure;
import fr.kissy.ae.evolution.utils.MutableInt;
import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseStateEvaluator implements FitnessEvaluator<BaseState> {
    private final HashMap<Structure, Integer> target;

    public BaseStateEvaluator(HashMap<Structure, Integer> target) {
        this.target = target;
    }

    public double getFitness(BaseState candidate, List<? extends BaseState> population) {
        candidate.processActionsUntilSatisfying(target);
        double fitness = 0;
        for (Map.Entry<Structure, MutableInt> entry : candidate.getStructureLevels().entrySet()) {
            fitness += Math.min(entry.getValue().get(), target.getOrDefault(entry.getKey(), 0)) * entry.getKey().getCredits();
        }
        fitness += candidate.getEconomy();
        fitness -= candidate.getInvalidActionCount();
        fitness /= candidate.getDuration() / 3600d;
        if (candidate.isSatisfyTarget()) {
            fitness *= 2;
        }
        fitness = Math.max(fitness, 0);
        candidate.setFitness(fitness);
        return fitness;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isNatural() {
        return true;
    }
}