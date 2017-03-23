package com.simulation.models.strategies;

import com.simulation.contracts.Strategy;

/**
 * This Strategy will cooperate until it's "betrayed". After that it will always be selfish.
 */
public class GrudgerStrategy implements Strategy {

    private boolean wasBetrayed;

    public GrudgerStrategy() {
        wasBetrayed = false;
    }

    @Override
    public boolean makeMove() {
        return !wasBetrayed;
    }

    @Override
    public void updateOpponentMove(boolean opponentMove) {
        if (!opponentMove) {
            this.wasBetrayed = true;
        }
    }

    @Override
    public void resetStrategy() {
        this.wasBetrayed = false;
    }
}
