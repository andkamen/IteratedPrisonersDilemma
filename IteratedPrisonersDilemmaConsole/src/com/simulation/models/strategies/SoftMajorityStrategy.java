package com.simulation.models.strategies;

import com.simulation.contracts.Strategy;

/**
 * Cooperates on the first move, and cooperates as long as the number of times the opponent has cooperated
 * is greater than or equal to the number of times it has defected, else it defects.
 */
public class SoftMajorityStrategy implements Strategy {

    private int opponentDefectCount;
    private int opponentCooperateCount;

    public SoftMajorityStrategy() {
        this.opponentCooperateCount = 0;
        this.opponentDefectCount = 0;
    }

    @Override
    public boolean makeMove() {
        if (this.opponentCooperateCount >= opponentDefectCount) {
            return true;
        }
        return false;
    }

    @Override
    public void updateOpponentMove(boolean opponentMove) {
        if (opponentMove) {
            this.opponentCooperateCount++;
        } else {
            this.opponentDefectCount++;
        }
    }

    @Override
    public void resetStrategy() {
        this.opponentCooperateCount = 0;
        this.opponentDefectCount = 0;
    }
}
