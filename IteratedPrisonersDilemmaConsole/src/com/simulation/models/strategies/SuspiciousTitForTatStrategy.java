package com.simulation.models.strategies;

import com.simulation.contracts.Strategy;

/**
 * Same as Tit for Tat, except that it defects on the first move
 */
public class SuspiciousTitForTatStrategy implements Strategy {

    private boolean isFirstMove;
    private boolean lastOpponentMove;

    public SuspiciousTitForTatStrategy() {
        this.isFirstMove = true;
    }

    @Override
    public boolean makeMove() {
        if (this.isFirstMove) {
            this.isFirstMove = false;
            return false;
        }
        return lastOpponentMove;
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
