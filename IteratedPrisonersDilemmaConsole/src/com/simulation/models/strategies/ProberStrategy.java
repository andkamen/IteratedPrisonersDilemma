package com.simulation.models.strategies;

import com.simulation.contracts.Strategy;

/**
 * Starts with D,C,C and then defects if the opponent has cooperated in the second and third move;
 * otherwise, it plays Tit For Tat.
 */
public class ProberStrategy implements Strategy {

    private boolean lastOpponentMove;
    private boolean[] openingMoves = {false, true, true};
    private int openingTurn;
    private boolean[] opponentFirstMoves;
    private boolean shouldAlwaysDefect;

    public ProberStrategy() {
        this.opponentFirstMoves = new boolean[3];
        this.openingTurn = 0;
    }

    @Override
    public boolean makeMove() {
        if (this.openingTurn < this.openingMoves.length) {
            boolean move = this.openingMoves[this.openingTurn];
            this.openingTurn++;
            return move;
        }

        if (shouldAlwaysDefect) {
            return false;
        }

        return lastOpponentMove;
    }

    @Override
    public void updateOpponentMove(boolean opponentMove) {
        this.lastOpponentMove = opponentMove;
        if (this.openingTurn <= this.openingMoves.length) {
            this.opponentFirstMoves[this.openingTurn - 1] = opponentMove;
        }

        if (this.openingTurn == 3) {
            if (this.opponentFirstMoves[1] && this.opponentFirstMoves[2]) {
                this.shouldAlwaysDefect = true;
            }
        }
    }

    @Override
    public void resetStrategy() {
        this.opponentFirstMoves = new boolean[3];
        this.openingTurn = 0;
    }
}
