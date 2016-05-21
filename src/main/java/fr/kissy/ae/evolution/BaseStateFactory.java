package fr.kissy.ae.evolution;

import com.google.common.collect.Lists;
import fr.kissy.ae.evolution.action.Action;
import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Guillaume Le Biller (<i>guillaume.lebiller@gmail.com</i>)
 * @version $Id$
 */
public class BaseStateFactory extends AbstractCandidateFactory<BaseState> {
    private final int actionCount;
    private final Integer possibleActionsSize;
    private final List<Action> possibleActions;

    public BaseStateFactory(int actionCount, List<Action> possibleActions) {
        this.actionCount = actionCount;
        this.possibleActionsSize = possibleActions.size();
        this.possibleActions = possibleActions;
    }

    public BaseState generateRandomCandidate(Random rng) {
        ArrayList<Action> actions = Lists.newArrayList();
        for (int i = 0; i < actionCount; i++) {
            actions.add(possibleActions.get(rng.nextInt(possibleActionsSize)));
        }
        return new BaseState(actions);
    }
}
