package com.core.contracts;

import com.simulation.contracts.Strategy;

public interface StrategyFactory {

    Strategy buildStrategy(String strategyType);
}
