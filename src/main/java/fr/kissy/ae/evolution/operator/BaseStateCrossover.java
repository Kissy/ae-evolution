package fr.kissy.ae.evolution.operator;

import com.google.common.collect.Lists;
import fr.kissy.ae.evolution.BaseState;
import fr.kissy.ae.evolution.action.Action;
import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.operators.AbstractCrossover;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Guillaume Le Biller (<i>guillaume.lebiller@gmail.com</i>)
 * @version $Id$
 */
public class BaseStateCrossover extends AbstractCrossover<BaseState> {
    public BaseStateCrossover(int crossoverPoints) {
        super(crossoverPoints);
    }

    public BaseStateCrossover(int crossoverPoints, Probability crossoverProbability) {
        super(crossoverPoints, crossoverProbability);
    }

    @Override
    protected List<BaseState> mate(BaseState parent1, BaseState parent2, int numberOfCrossoverPoints, Random rng) {
        BaseState offspring1 = parent1.copy();
        BaseState offspring2 = parent2.copy();

        // Apply as many cross-overs as required.
        for (int i = 0; i < numberOfCrossoverPoints; i++) {
            // Cross-over index is always greater than zero and less than
            // the length of the parent so that we always pick a point that
            // will result in a meaningful cross-over.
            int max = Math.min(offspring1.getActions().size(), offspring2.getActions().size());
            // Don't perform cross-over if there aren't at least 2 elements in each list.
            if (max > 1) {
                int crossoverIndex = (1 + rng.nextInt(max - 1));
                for (int j = 0; j < crossoverIndex; j++) {
                    Action temp = offspring1.getActions().get(j);
                    offspring1.getActions().set(j, offspring2.getActions().get(j));
                    offspring2.getActions().set(j, temp);
                }
            }
        }

        ArrayList<BaseState> results = Lists.newArrayList();
        results.add(offspring1);
        results.add(offspring2);
        return results;
    }
}
