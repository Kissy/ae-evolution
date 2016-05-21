package fr.kissy.ae.evolution.action;

import com.google.common.base.Objects;
import fr.kissy.ae.evolution.BaseState;
import fr.kissy.ae.evolution.data.Structure;

/**
 * @author Guillaume Le Biller (<i>guillaume.lebiller@gmail.com</i>)
 * @version $Id$
 */
public class BuildStructure implements Action {
    private final Structure structure;

    public BuildStructure(Structure structure) {
        this.structure = structure;
    }

    @Override
    public boolean isValid(BaseState state) {
        return state.canBuild(structure);
    }

    @Override
    public void apply(BaseState state) {
        state.buildStructure(structure);
    }

    public Structure getStructure() {
        return structure;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("structure", structure)
                .toString();
    }
}
