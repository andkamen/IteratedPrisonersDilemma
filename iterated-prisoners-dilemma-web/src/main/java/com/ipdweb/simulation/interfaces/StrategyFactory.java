package com.ipdweb.simulation.interfaces;

import com.ipdweb.entities.strategy.interfaces.Strategy;

public interface StrategyFactory {

    Strategy buildStrategy(String strategyType);
}
