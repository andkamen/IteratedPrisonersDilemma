package com.simulation.models.strategies;

import com.simulation.contracts.Strategy;

/**
 * Defects on the first move, and defects if the number of defections of the opponent
 * is greater than or equal to the number of times it has cooperated, else cooperates.
 */
public class HardMajorityStrategy implements Strategy {

    private int opponentDefectCount;
    private int opponentCooperateCount;

    public HardMajorityStrategy() {
        this.opponentCooperateCount = 0;
        this.opponentDefectCount = 0;
    }

    @Override
    public boolean makeMove() {
        if (this.opponentDefectCount >= opponentCooperateCount) {
            return false;
        }
        return true;
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
