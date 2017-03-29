package com.ipdweb.areas.tournament.models.viewModels;

import com.ipdweb.areas.strategy.models.viewModels.StrategyViewModel;

import java.util.List;

public class TournamentPreviewViewModel {

    private long id;

    private String name;

    private List<StrategyViewModel> strategies;

    private int roundCount;

    public TournamentPreviewViewModel() {
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
}
