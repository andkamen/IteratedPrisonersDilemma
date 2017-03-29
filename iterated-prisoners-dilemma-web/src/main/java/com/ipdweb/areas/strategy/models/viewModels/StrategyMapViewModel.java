package com.ipdweb.areas.strategy.models.viewModels;

import java.util.HashMap;
import java.util.Map;

public class StrategyMapViewModel {

    private Map<String, Integer> strategies;

    public StrategyMapViewModel() {
        this.strategies = new HashMap<>();
    }

    public Map<String, Integer> getStrategies() {
        return strategies;
    }

    public void setStrategies(Map<String, Integer> strategies) {
        this.strategies = strategies;
    }
}
