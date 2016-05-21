package fr.kissy.ae.evolution.action;

import fr.kissy.ae.evolution.BaseState;

/**
 * @author Guillaume Le Biller (<i>guillaume.lebiller@gmail.com</i>)
 * @version $Id$
 */
public interface Action {
    boolean isValid(BaseState state);
    void apply(BaseState state);
}
