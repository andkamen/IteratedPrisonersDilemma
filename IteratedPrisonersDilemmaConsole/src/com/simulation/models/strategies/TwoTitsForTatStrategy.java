package com.simulation.models.strategies;

import com.simulation.contracts.Strategy;

/**
 * Same as Tit for Tat except that it defects twice when the opponent defects.
 */
public class TwoTitsForTatStrategy implements Strategy {

    private boolean isFirstMove;
    private int consecutivePunishMoves;

    public TwoTitsForTatStrategy() {
        this.isFirstMove = true;
        this.consecutivePunishMoves = 0;
    }

    @Override
    public boolean makeMove() {
        if (this.isFirstMove) {
            this.isFirstMove = false;
            return true;
        }
        if (this.consecutivePunishMoves > 0) {
            this.consecutivePunishMoves--;
            return false;
        }
        return true;
    }

    @Override
    public void updateOpponentMove(boolean opponentMove) {
        if (!opponentMove) {
            this.consecutivePunishMoves = 2;
        }
    }

    @Override
    public void resetStrategy() {
        this.isFirstMove = true;
        this.consecutivePunishMoves = 0;
    }
}
