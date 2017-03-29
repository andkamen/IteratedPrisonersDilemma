package com.ipdweb.areas.strategy.factories;

import com.ipdweb.areas.strategy.entities.StrategyImpl;

import java.util.List;

public interface StrategyFactory {

    StrategyImpl buildStrategy(String strategyType);

    StrategyImpl hackStrategy(String strategyType,List<StrategyImpl> strategies);
}
