package com.simulation.contracts;

import java.util.Map;

/**
 * Interface that Generations should implement.
 * Match up various Strategies vs one another to see how well they do.
 */
public interface Generation extends AddStrategy,RemoveStrategy {

    Map<String, Integer> getStrategyScores();

    int getTotalStrategyCount();

    String getName();

    /**
     * Matches every registered strategy vs every other one and saves their performance
     */
    void playOut();

    String print(boolean isVerboseMode);
}
