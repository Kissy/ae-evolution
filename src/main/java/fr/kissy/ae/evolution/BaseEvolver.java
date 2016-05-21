package fr.kissy.ae.evolution;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import fr.kissy.ae.evolution.action.Action;
import fr.kissy.ae.evolution.action.BuildStructure;
import fr.kissy.ae.evolution.data.Structure;
import fr.kissy.ae.evolution.operator.BaseStateCrossover;
import fr.kissy.ae.evolution.operator.BaseStateDeletion;
import fr.kissy.ae.evolution.operator.BaseStateInsertion;
import fr.kissy.ae.evolution.operator.BaseStateSwap;
import fr.kissy.ae.evolution.operator.BaseStateTwiddle;
import org.uncommons.maths.number.ConstantGenerator;
import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.EvolutionEngine;
import org.uncommons.watchmaker.framework.EvolutionObserver;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.GenerationalEvolutionEngine;
import org.uncommons.watchmaker.framework.PopulationData;
import org.uncommons.watchmaker.framework.TerminationCondition;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.termination.Stagnation;

import java.util.HashMap;
import java.util.List;

public final class BaseEvolver {
    private static final List<Action> ACTIONS = Lists.<Action>newArrayList(
            new BuildStructure(Structure.URBAN_STRUCTURES),
            new BuildStructure(Structure.SOLAR_PLANTS),
            new BuildStructure(Structure.GAS_PLANTS),
            //new BuildStructure(Structure.FUSION_PLANTS),
            //new BuildStructure(Structure.ANTIMATTER_PLANTS),
            //new BuildStructure(Structure.ORBITAL_PLANTS),
            new BuildStructure(Structure.RESEARCH_LABS),
            new BuildStructure(Structure.METAL_REFINERIES),
            new BuildStructure(Structure.CRYSTAL_MINES),
            new BuildStructure(Structure.ROBOTIC_FACTORIES),
            new BuildStructure(Structure.SHIPYARDS),
            //new BuildStructure(Structure.ORBITAL_SHIPYARDS),
            new BuildStructure(Structure.SPACEPORTS)//,
            //new BuildStructure(Structure.COMMAND_CENTERS),
            //new BuildStructure(Structure.NANITE_FACTORIES),
            //new BuildStructure(Structure.ANDROID_FACTORIES),
            //new BuildStructure(Structure.ECONOMIC_CENTERS),
            //new BuildStructure(Structure.TERRAFORM),
            //new BuildStructure(Structure.MULTI_LEVEL_PLATFORMS),
            //new BuildStructure(Structure.ORBITAL_BASE),
            //new BuildStructure(Structure.JUMP_GATE),
            //new BuildStructure(Structure.BIOSPHERE_MODIFICATION),
            //new BuildStructure(Structure.CAPITAL)
    );

    public static void main(String[] args) {
        HashMap<Structure, Integer> targetStructures = Maps.newHashMap();
        targetStructures.put(Structure.METAL_REFINERIES, 5);
        targetStructures.put(Structure.ROBOTIC_FACTORIES, 5);
        List<EvolutionaryOperator<BaseState>> operators = Lists.newArrayList(
                new BaseStateCrossover(2),
                new BaseStateInsertion(ACTIONS, new ConstantGenerator<Probability>(new Probability(0.05d))),
                new BaseStateDeletion(ACTIONS, new ConstantGenerator<Probability>(new Probability(0.05d))),
                new BaseStateTwiddle(ACTIONS, new ConstantGenerator<Probability>(new Probability(0.05d))),
                new BaseStateSwap(ACTIONS, new ConstantGenerator<Probability>(new Probability(0.05d)))
        );
        EvolutionEngine<BaseState> engine = new GenerationalEvolutionEngine<BaseState>(
                new BaseStateFactory(100, ACTIONS),
                new EvolutionPipeline<BaseState>(operators),
                new BaseStateEvaluator(targetStructures),
                new RouletteWheelSelection(),
                new MersenneTwisterRNG()
        );
        engine.addEvolutionObserver(new EvolutionLogger());
        //TerminationCondition terminationCondition = new GenerationCount(1000000);
        TerminationCondition terminationCondition = new Stagnation(10000, true);
        //TerminationCondition terminationCondition = new TargetFitness(1, true);
        BaseState result = engine.evolve(100, 0, terminationCondition);
        System.out.println("Evolution result: " + result);
    }

    private static class EvolutionLogger implements EvolutionObserver<BaseState> {
        public void populationUpdate(PopulationData<? extends BaseState> data) {
            System.out.printf("Generation %d: %s - %s\n", data.getGenerationNumber(), data.getBestCandidate(), data.getBestCandidateFitness());
        }
    }
}