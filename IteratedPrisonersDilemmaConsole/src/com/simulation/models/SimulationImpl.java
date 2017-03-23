package com.simulation.models;

import com.core.contracts.StrategyFactory;
import com.exceptions.LockedSimulationException;
import com.simulation.contracts.Generation;
import com.simulation.contracts.Simulation;
import com.simulation.contracts.Strategy;

import java.util.*;

public class SimulationImpl implements Simulation {

    private String name;
    private List<Generation> generations;
    private StrategyFactory strategyFactory;
    private boolean isLocked; //locks simulation for addition or removal of strategies after its started.

    public SimulationImpl(String name, StrategyFactory strategyFactory) {
        this.name = name;
        this.strategyFactory = strategyFactory;
        this.generations = new ArrayList<>();
        this.generations.add(new GenerationImpl());
        this.isLocked = false;
    }


    @Override
    public String getName() {
        return this.name;
    }

    public int getGenerationSize() {
        return this.generations.size();
    }

    //Add and remove strats from the generation 0 before a sim begins.
    @Override
    public String addStrategy(Strategy strategy) {
        if (this.isLocked){
            throw new LockedSimulationException(this.getName());
        }
        return this.generations.get(0).addStrategy(strategy);
    }

    //TODO add remove command?
    @Override
    public String removeStrategy(String name) {
        if (this.isLocked){
            throw new LockedSimulationException(this.getName());
        }
        return this.generations.get(0).removeStrategy(name);
    }

    @Override
    public void run(int generationCount) {
        this.isLocked = true;
        Generation nextGeneration = null;

        for (int i = 0; i < generationCount; i++) {
            this.generations.get(this.generations.size() - 1).playOut();

            nextGeneration = setUpNextGeneration(this.generations.get(this.generations.size() - 1));

            this.generations.add(nextGeneration);
        }
    }

    /// http://stackoverflow.com/questions/13483430/how-to-make-rounded-percentages-add-up-to-100
    // the answer by paxdiablo has the algorithm used for rounding up the percentages with least amount of error.
    private Generation setUpNextGeneration(Generation currentGeneration) {
        Generation nextGeneration = new GenerationImpl();

        //Get the current gen info: Total places, all strats and their scores
        Map<String, Integer> strategyScores = currentGeneration.getStrategyScores();
        int nextGenPlaceCount = currentGeneration.getTotalStrategyCount();

        //the relation list will give us info about which element in the calc list corresponds to which strategy
        String[] strategyRelation = new String[nextGenPlaceCount + 1];
        strategyRelation[0] = "Placeholder";
        double[] strategyDistribution = new double[nextGenPlaceCount + 1];
        strategyDistribution[0] = 0d;

        double totalScore = 0;
        for (Integer strategyTypeTotalScore : strategyScores.values()) {
            totalScore += strategyTypeTotalScore;
        }

        //fill in the arrays with the strategies and their corresponding % of total score
        int tempIndex = 1;
        for (Map.Entry<String, Integer> strategyScoreEntry : strategyScores.entrySet()) {
            strategyRelation[tempIndex] = strategyScoreEntry.getKey();
            strategyDistribution[tempIndex++] = strategyScoreEntry.getValue() / totalScore;
        }

        strategyDistribution = calculateNextGenDistribution(nextGenPlaceCount, strategyDistribution);

        for (int i = 1; i < strategyRelation.length; i++) {
            String strategyName = strategyRelation[i];
            int strategyCount = (int) strategyDistribution[i];
            for (int j = 0; j < strategyCount; j++) {
                nextGeneration.addStrategy(this.strategyFactory.buildStrategy(strategyName.replace("Strategy", "")));
            }
        }
        return nextGeneration;
    }

    private double[] calculateNextGenDistribution(int nextGenPlaceCount, double[] strategyDistribution) {
        //calculate how many spots strategies should take in next generation (as a fraction)
        for (int i = 1; i < strategyDistribution.length; i++) {
            strategyDistribution[i] *= nextGenPlaceCount;
        }

        //add up previous elements to each element
        for (int i = 1; i < strategyDistribution.length; i++) {
            strategyDistribution[i] += strategyDistribution[i - 1];
        }
        //round the results to nearest integer
        for (int i = 1; i < strategyDistribution.length; i++) {
            strategyDistribution[i] = (double) Math.round(strategyDistribution[i]);
        }

        //get final values for number of spots
        for (int i = strategyDistribution.length - 1; i > 0; i--) {
            strategyDistribution[i] -= strategyDistribution[i - 1];
        }

        return strategyDistribution;
    }

    public String print(int genNum, boolean isVerboseMode) {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("   Generation %d:", genNum + 1))
                .append(System.lineSeparator());
        sb.append(this.generations.get(genNum).print(isVerboseMode));

        return sb.toString();
    }
}
