package com.ipdweb.areas.simulation.entities;

import com.ipdweb.areas.strategy.entities.StrategyImpl;
import com.ipdweb.areas.strategy.entities.interfaces.Strategy;
import com.ipdweb.areas.utils.Constants;

import javax.persistence.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Entity
@Table(name = "generations")
public class Generation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    private String name;

    @ManyToMany()
    @JoinTable(
            joinColumns = @JoinColumn(name = "generation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "strategy_id", referencedColumnName = "id"))
    private List<StrategyImpl> strategies;

    @Transient
    private Map<String, Integer> strategyScores;

    @Transient
    private Map<String, Integer> strategyCount;

    //TODO fetch type lazy?
    @OneToMany(mappedBy = "generation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<GenerationMatchUpResult> generationMatchUpResults;

    @ManyToOne()
    @JoinColumn(name = "simulation_id")
    private Simulation simulation;

    @Column(name = "round_Count")
    private int roundCount;

    @Transient
    private Random rand;

    public Generation(Simulation simulation, String name) {
        this.simulation = simulation;

        this.strategyScores = new HashMap<>();
        this.strategyCount = new HashMap<>();
        this.strategies = new ArrayList<>();
        this.generationMatchUpResults = new ArrayList<>();
        this.rand = new Random();
        this.name = name;

        setNewRoundCount();
    }

    public Generation() {
        this.strategyScores = new HashMap<>();
        this.strategyCount = new HashMap<>();
        this.rand = new Random();
    }

    public Map<String, Integer> getStrategyScores() {
        return strategyScores;
    }

    public int getTotalStrategyCount() {
        return this.strategies.size();
    }


    public void addStrategy(StrategyImpl strategy) {
        //add strategy to list
//        try {
//            this.strategies.add(strategy.getClass().getConstructor().newInstance());
//        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
//            e.printStackTrace();
//        }

        this.strategies.add(strategy);

        //increment strategy Type counter and strategy Type total score counter
        if (!strategyCount.containsKey(strategy.getName())) {
            this.strategyCount.put(strategy.getName(), 0);
            this.strategyScores.put(strategy.getName(), 0);
        }
        this.strategyCount.put(strategy.getName(), this.strategyCount.get(strategy.getName()) + 1);
    }

    public void removeStrategy(String strategyName) {
        boolean foundMatch = false;
        StrategyImpl strategyToRemove = null;

        for (StrategyImpl strategy : this.strategies) {
            if (strategy.getName().equals(strategyName)) {
                foundMatch = true;
                strategyToRemove = strategy;
            }
        }
        //remove strategy from list and decrement counters. If 0 strategy of given type are left, remove them from dictionaries
        if (foundMatch) {
            this.strategies.remove(strategyToRemove);
            this.strategyCount.put(strategyName, this.strategyCount.get(strategyToRemove.getName()) - 1);
            int count = this.strategyCount.get(strategyName);
            if (this.strategyCount.get(strategyName) == 0) {
                this.strategyCount.remove(strategyName);
                this.strategyScores.remove(strategyName);
            }
        }
    }

    public void playOut() {
        Strategy stratA = null;
        Strategy stratB = null;
        int strategyScore;
        GenerationMatchUpResult generationMatchUpResult;

        for (int a = 0; a < this.strategies.size(); a++) {
            stratA = this.strategies.get(a);
            for (int b = 0; b < this.strategies.size(); b++) {
                if (b <= a) {
                    continue;
                }
                stratB = this.strategies.get(b);

                generationMatchUpResult = matchUp(stratA, stratB);
                this.generationMatchUpResults.add(generationMatchUpResult);

                strategyScore = this.strategyScores.get(stratA.getName()) + generationMatchUpResult.getStratAScore();
                this.strategyScores.put(stratA.getName(), strategyScore);

                strategyScore = this.strategyScores.get(stratB.getName()) + generationMatchUpResult.getStratBScore();
                this.strategyScores.put(stratB.getName(), strategyScore);
            }
        }
    }

    private GenerationMatchUpResult matchUp(Strategy stratA, Strategy stratB) {

        try {
            stratA = stratA.getClass().getConstructor().newInstance();
            stratB = stratB.getClass().getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        GenerationMatchUpResult generationMatchUpResult = new GenerationMatchUpResult(stratA.getName(), stratB.getName(), this);

        boolean stratAMove;
        boolean stratBMove;
        int[] roundResult; // 0 for stratA , 1 for stratB
        int[] matchUpResult = new int[2];

        stratA.resetStrategy();
        stratB.resetStrategy();

        for (int i = 0; i < this.roundCount; i++) {
            stratAMove = stratA.makeMove();
            stratBMove = stratB.makeMove();

            stratA.updateOpponentMove(stratBMove);
            stratB.updateOpponentMove(stratAMove);

            roundResult = determineRoundResult(stratAMove, stratBMove);
            matchUpResult[0] += roundResult[0];
            matchUpResult[1] += roundResult[1];
        }

        generationMatchUpResult.setStratAScore(matchUpResult[0]);
        generationMatchUpResult.setStratBScore(matchUpResult[1]);

        return generationMatchUpResult;
    }

    private int[] determineRoundResult(boolean stratAMove, boolean stratBMove) {
        int[] roundResult = new int[2];

        if (stratAMove && stratBMove) {
            //Both cooperate
            roundResult[0] = Constants.COOPERATE_COOPERATE_SCORE;
            roundResult[1] = Constants.COOPERATE_COOPERATE_SCORE;
        } else if (!stratAMove && !stratBMove) {
            //both defect
            roundResult[0] = Constants.DEFECT_DEFECT_SCORE;
            roundResult[1] = Constants.DEFECT_DEFECT_SCORE;
        } else if (!stratAMove) {
            //A defects B cooperates
            roundResult[0] = Constants.DEFECT_COOPERATE_SCORE;
            roundResult[1] = Constants.COOPERATE_DEFECT_SCORE;
        } else {
            //B defects A cooperates
            roundResult[0] = Constants.COOPERATE_DEFECT_SCORE;
            roundResult[1] = Constants.DEFECT_COOPERATE_SCORE;
        }
        return roundResult;
    }

    private void setNewRoundCount() {
        //Random number between A and B = rand((B-A)+1) + A
        int newRoundCount = this.rand.nextInt((Constants.MAX_GENERATION_ROUND_COUNT - Constants.MIN_GENERATION_ROUND_COUNT) + 1) + Constants.MIN_GENERATION_ROUND_COUNT;
        this.setRoundCount(newRoundCount);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StrategyImpl> getStrategies() {
        return strategies;
    }

    public void setStrategies(List<StrategyImpl> strategies) {
        this.strategies = strategies;
    }

    public List<GenerationMatchUpResult> getGenerationMatchUpResults() {
        return generationMatchUpResults;
    }

    public void setGenerationMatchUpResults(List<GenerationMatchUpResult> generationMatchUpResults) {
        this.generationMatchUpResults = generationMatchUpResults;
    }

    public int getRoundCount() {
        return roundCount;
    }

    public void setRoundCount(int roundCount) {
        this.roundCount = roundCount;
    }

    public void setStrategyScores(Map<String, Integer> strategyScores) {
        this.strategyScores = strategyScores;
    }

    public Map<String, Integer> getStrategyCount() {
        return strategyCount;
    }

    public void setStrategyCount(Map<String, Integer> strategyCount) {
        this.strategyCount = strategyCount;
    }

    public Simulation getSimulation() {
        return simulation;
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }
}
