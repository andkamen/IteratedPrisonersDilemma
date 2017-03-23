package com.simulation.models.strategies;

import com.simulation.contracts.Strategy;

/**
 * This Strategy will always cooperate.
 */
public class AlwaysCooperateStrategy implements Strategy {

    public AlwaysCooperateStrategy() {

    }

    @Override
    public boolean makeMove() {
        //always cooperates
        return true;
    }

    @Override
    public void updateOpponentMove(boolean opponentMove) {
        //doesn't need opponent move info because it always cooperates
    }

    @Override
    public void resetStrategy() {
        //nothing changes in the strat. Nothing to reset
    }
}
