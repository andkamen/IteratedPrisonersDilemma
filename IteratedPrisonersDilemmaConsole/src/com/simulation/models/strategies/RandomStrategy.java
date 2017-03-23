package com.simulation.models.strategies;

import com.simulation.contracts.Strategy;

import java.util.Random;

/**
 * This Strategy will be random and unpredictable.
 */
public class RandomStrategy implements Strategy {
    private Random rand;

    public RandomStrategy() {
        this.rand = new Random();
    }

    @Override
    public boolean makeMove() {
        return this.rand.nextBoolean();
    }

    @Override
    public void updateOpponentMove(boolean opponentMove) {
        //doesn't need opponent move info because its always random
    }

    @Override
    public void resetStrategy() {
        //nothing changes in the strat. Nothing to reset
    }
}
