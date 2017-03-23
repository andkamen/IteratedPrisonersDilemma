package com.simulation.models.strategies;

import com.simulation.contracts.Strategy;

/**
 * Cooperates on the first move, and cooperates except after receiving a sucker payoff.
 */
public class FirmButFairStrategy implements Strategy {

    private boolean isFirstMove;
    private boolean lastOpponentMove;
    private boolean lastMove;

    public FirmButFairStrategy() {
        this.isFirstMove = true;
    }

    @Override
    public boolean makeMove() {
        if (this.isFirstMove) {
            this.isFirstMove = false;

            this.lastMove = true;
            return true;
        }
        if (!this.lastOpponentMove && this.lastMove) {
            this.lastMove = false;
            return false;
        }

        this.lastMove = true;
        return true;
    }

    @Override
    public void updateOpponentMove(boolean opponentMove) {
        this.lastOpponentMove = opponentMove;
    }

    @Override
    public void resetStrategy() {
        this.isFirstMove = true;
    }
}
