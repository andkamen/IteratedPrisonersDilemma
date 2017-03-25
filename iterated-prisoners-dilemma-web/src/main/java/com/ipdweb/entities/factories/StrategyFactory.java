package com.ipdweb.entities.factories;

import com.ipdweb.entities.strategy.StrategyImpl;

import java.util.List;

public interface StrategyFactory {

    StrategyImpl buildStrategy(String strategyType);

    StrategyImpl hackStrategy(String strategyType,List<StrategyImpl> strategies);
}
