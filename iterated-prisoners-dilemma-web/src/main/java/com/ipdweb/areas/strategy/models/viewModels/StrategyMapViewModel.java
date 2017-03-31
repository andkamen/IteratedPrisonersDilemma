package com.ipdweb.areas.strategy.models.viewModels;

import java.util.LinkedHashMap;
import java.util.Map;

public class StrategyMapViewModel {

    private Map<String, Integer> strategies;

    public StrategyMapViewModel() {
        this.strategies = new LinkedHashMap<>();
    }

    public Map<String, Integer> getStrategies() {
        return strategies;
    }

    public void setStrategies(Map<String, Integer> strategies) {
        this.strategies = strategies;
    }
}
