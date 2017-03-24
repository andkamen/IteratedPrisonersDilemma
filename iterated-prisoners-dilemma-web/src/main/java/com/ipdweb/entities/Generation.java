package com.ipdweb.entities;

import com.ipdweb.entities.strategy.StrategyImpl;
import com.ipdweb.entities.strategy.interfaces.Strategy;
import com.ipdweb.exceptions.InsufficientRegisteredStrategiesException;
import com.ipdweb.simulation.interfaces.MatchUpResult;
import com.ipdweb.simulation.models.MatchUpResultImpl;
import com.ipdweb.utils.Constants;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private List<Integer> strategyScores;

    @OneToMany(mappedBy = "generation", cascade = CascadeType.ALL)
    private List<GenerationMatchUpResult> generationMatchUpResults;

    @ManyToOne()
    @JoinColumn(name = "simulation_id")
    private Simulation simulation;

    @Column(name = "round_Count")
    private int roundCount;

    @Transient
    private Random rand;

    public Generation() {
        this.strategyScores = new ArrayList<>();
        this.strategies = new ArrayList<>();
        this.generationMatchUpResults = new ArrayList<>();
        this.rand = new Random();

        setNewRoundCount();
    }

    public void addStrategy(StrategyImpl strategy) {
        this.strategies.add(strategy);
        this.strategyScores.add(0);
    }

    public void removeStrategy(String strategy) {
        boolean foundMatch = false;
        int indexToRemove = 0;

        for (int i = 0; i < strategies.size(); i++) {
            if (strategies.get(i).getName().equals(strategy)) {
                foundMatch = true;
                indexToRemove = i;
                break;
            }
        }

        if (foundMatch) {
            strategies.remove(indexToRemove);
            strategyScores.remove(indexToRemove);
        }
    }

    public void playOut() {
        //TODO remove exception? validate from controller?
        if (strategies.size() < 2) {
            throw new InsufficientRegisteredStrategiesException();
        }

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

                strategyScore = this.strategyScores.get(a) + generationMatchUpResult.getStratAScore();
                this.strategyScores.set(a, strategyScore);

                strategyScore = this.strategyScores.get(b) + generationMatchUpResult.getStratBScore();
                this.strategyScores.set(b, strategyScore);
            }
        }
    }

    private GenerationMatchUpResult matchUp(Strategy stratA, Strategy stratB) {

        GenerationMatchUpResult generationMatchUpResult = new GenerationMatchUpResult(stratA.getName(), stratB.getName(), this);

        boolean stratAMove;
        boolean stratBMove;
        boolean[] allStratAMoves = new boolean[this.roundCount];
        boolean[] allStratBMoves = new boolean[this.roundCount];
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
        MatchUpResult result = new MatchUpResultImpl(stratA.getClass().getSimpleName(), stratB.getClass().getSimpleName(), allStratAMoves, allStratBMoves, matchUpResult[0], matchUpResult[1]);

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
}
