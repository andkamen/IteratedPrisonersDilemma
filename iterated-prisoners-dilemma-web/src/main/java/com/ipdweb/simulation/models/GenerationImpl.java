package com.ipdweb.simulation.models;


import com.ipdweb.exceptions.InsufficientRegisteredStrategiesException;
import com.ipdweb.simulation.interfaces.Generation;
import com.ipdweb.simulation.interfaces.MatchUpResult;
import com.ipdweb.entities.strategy.interfaces.Strategy;
import com.ipdweb.utils.Constants;
import com.ipdweb.utils.Messages;

import java.util.*;
import java.util.stream.Collectors;

public class GenerationImpl implements Generation {

    private String name;
    private List<Strategy> strategies;
    private Map<String, Integer> strategyScores;
    private Map<String, Integer> strategyCount;
    private List<MatchUpResult> matchUpResults;
    private int roundCount;
    private Random rand;

    /**
     * Creates a generation that can store various strategy and test them vs one another
     */
    public GenerationImpl() {
        this(null);
    }

    public GenerationImpl(String name) {
        this.strategies = new ArrayList<>();
        this.strategyScores = new HashMap<>();
        this.strategyCount = new HashMap<>();
        this.matchUpResults = new ArrayList<>();
        this.rand = new Random();
        this.name = name;

        this.setNewRoundCount();
    }

    private void setRoundCount(int roundCount) {
        this.roundCount = roundCount;
    }

    public Map<String, Integer> getStrategyScores() {
        return strategyScores;
    }

    public int getTotalStrategyCount() {
        return this.strategies.size();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String addStrategy(Strategy strategy) {
        //add strategy to list
        this.strategies.add(strategy);

        //increment strategy Type counter and strategy Type total score counter
        if (!strategyCount.containsKey(strategy.getClass().getSimpleName())) {
            this.strategyCount.put(strategy.getClass().getSimpleName(), 0);
            this.strategyScores.put(strategy.getClass().getSimpleName(), 0);
        }
        this.strategyCount.put(strategy.getClass().getSimpleName(), this.strategyCount.get(strategy.getClass().getSimpleName()) + 1);

        return String.format(Messages.SUCCESSFULLY_REGISTERED_STRATEGY, strategy.getClass().getSimpleName());
    }

    @Override
    public String removeStrategy(String simpleName) {
        boolean foundMatch = false;
        Strategy strategyToRemove = null;

        for (Strategy strategy : this.strategies) {
            if (strategy.getClass().getSimpleName().equals(simpleName)) {
                foundMatch = true;
                strategyToRemove = strategy;
            }
        }
        //remove strategy from list and decrement counters. If 0 strategy of given type are left, remove them from dictionaries
        if (foundMatch) {
            this.strategies.remove(strategyToRemove);
            this.strategyCount.put(simpleName, this.strategyCount.get(strategyToRemove.getClass().getSimpleName()) - 1);
            int count = this.strategyCount.get(simpleName);
            if (this.strategyCount.get(simpleName) == 0) {
                this.strategyCount.remove(simpleName);
                this.strategyScores.remove(simpleName);
            }

            return String.format(Messages.SUCCESSFULLY_REMOVED_STRATEGY, simpleName);
        }

        return String.format(Messages.ITEM_NOT_REGISTERED, simpleName);
    }

    @Override
    public void playOut() {
        if (strategies.size() < 2) {
            throw new InsufficientRegisteredStrategiesException();
        }

        Strategy stratA = null;
        Strategy stratB = null;
        int strategyScore;
        MatchUpResult matchUpResult;

        for (int i = 0; i < this.strategies.size(); i++) {
            stratA = this.strategies.get(i);
            for (int j = 0; j < this.strategies.size(); j++) {
                if (j <= i) {
                    continue;
                }
                stratB = this.strategies.get(j);

                matchUpResult = matchUp(stratA, stratB);
                this.matchUpResults.add(matchUpResult);
                // System.out.println(matchUpResult.toString());

                strategyScore = this.strategyScores.get(stratA.getClass().getSimpleName()) + matchUpResult.getStratAScore();
                this.strategyScores.put(stratA.getClass().getSimpleName(), strategyScore);

                strategyScore = this.strategyScores.get(stratB.getClass().getSimpleName()) + matchUpResult.getStratBScore();
                this.strategyScores.put(stratB.getClass().getSimpleName(), strategyScore);
                //System.out.println();
            }
        }
    }

    private MatchUpResult matchUp(Strategy stratA, Strategy stratB) {

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
            allStratAMoves[i] = stratAMove;

            stratBMove = stratB.makeMove();
            allStratBMoves[i] = stratBMove;

            stratA.updateOpponentMove(stratBMove);
            stratB.updateOpponentMove(stratAMove);

            roundResult = determineRoundResult(stratAMove, stratBMove);
            matchUpResult[0] += roundResult[0];
            matchUpResult[1] += roundResult[1];
        }

        MatchUpResult result = new MatchUpResultImpl(stratA.getClass().getSimpleName(), stratB.getClass().getSimpleName(), allStratAMoves, allStratBMoves, matchUpResult[0], matchUpResult[1]);

        return result;
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
        int newRoundCount = this.rand.nextInt((Constants.MAX_GENERATION_ROUND_COUNT - Constants.MIN_GENERATION_ROUND_COUNT) + 1)
                + Constants.MIN_GENERATION_ROUND_COUNT;
        this.setRoundCount(newRoundCount);
    }

    public String print(boolean isVerboseMode) {
        StringBuilder sb = new StringBuilder();

        this.strategyScores = this.strategyScores.entrySet()
                .stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        for (Map.Entry<String, Integer> entry : this.strategyScores.entrySet()) {
            //TODO add constant for alignment length?
            sb.append(String.format("%-30s (%3d) -> %9d", entry.getKey(), this.strategyCount.get(entry.getKey()), entry.getValue()))
                    .append(System.lineSeparator());
        }
        sb.append(System.lineSeparator());

        if (isVerboseMode) {
            for (MatchUpResult matchUpResult : matchUpResults) {
                sb.append(matchUpResult.toString())
                        .append(System.lineSeparator());
            }
        }
        return sb.toString();
    }
}
