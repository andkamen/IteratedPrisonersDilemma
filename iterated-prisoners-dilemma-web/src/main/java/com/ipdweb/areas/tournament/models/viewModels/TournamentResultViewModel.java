package com.ipdweb.areas.tournament.models.viewModels;

import com.ipdweb.areas.strategy.models.viewModels.StrategyKeyValueViewModel;
import com.ipdweb.areas.strategy.models.viewModels.StrategyViewModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TournamentResultViewModel {

    private String id;

    private String name;

    private List<StrategyViewModel> strategies;

    private List<Integer> strategyScores;

    private List<StrategyKeyValueViewModel> strategyScoreKVPairs;

    private int roundCount;

    public TournamentResultViewModel() {
        this.strategyScores = new ArrayList<>();
        this.strategyScoreKVPairs = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<StrategyKeyValueViewModel> getStrategyScoreKVPairs() {
        return strategyScoreKVPairs;
    }

    public void setStrategyScoreKVPairs(List<StrategyKeyValueViewModel> strategyScoreKVPairs) {
        this.strategyScoreKVPairs = strategyScoreKVPairs;
    }
}
