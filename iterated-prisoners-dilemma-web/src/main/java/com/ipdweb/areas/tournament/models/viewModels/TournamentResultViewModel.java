package com.ipdweb.areas.tournament.models.viewModels;

import com.ipdweb.areas.strategy.models.viewModels.StrategyViewModel;

import java.util.ArrayList;
import java.util.List;

public class TournamentResultViewModel {

    private String name;

    private List<StrategyViewModel> strategies;

    private List<Integer> strategyScores;

    private int roundCount;

    public TournamentResultViewModel() {
        this.strategyScores = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StrategyViewModel> getStrategies() {
        return strategies;
    }

    public void setStrategies(List<StrategyViewModel> strategies) {
        this.strategies = strategies;
    }

    public int getRoundCount() {
        return roundCount;
    }

    public void setRoundCount(int roundCount) {
        this.roundCount = roundCount;
    }

    public List<Integer> getStrategyScores() {
        return strategyScores;
    }

    public void setStrategyScores(List<Integer> strategyScores) {
        this.strategyScores = strategyScores;
    }
}
