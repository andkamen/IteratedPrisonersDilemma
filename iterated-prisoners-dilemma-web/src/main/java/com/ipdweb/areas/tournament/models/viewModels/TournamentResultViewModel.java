package com.ipdweb.areas.tournament.models.viewModels;

import com.ipdweb.areas.strategy.models.viewModels.StrategyViewModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TournamentResultViewModel {

    private String name;

    private List<StrategyViewModel> strategies;

    private List<Integer> strategyScores;

    private Map<String, Integer> strategiesMap;

    private int roundCount;

    public TournamentResultViewModel() {
        this.strategyScores = new ArrayList<>();
        this.strategiesMap = new LinkedHashMap<>();
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

    public Map<String, Integer> getStrategiesMap() {
        return strategiesMap;
    }

    public void setStrategiesMap(Map<String, Integer> strategiesMap) {
        this.strategiesMap = strategiesMap;
    }
}
