package com.simulation.models.strategies;

import com.simulation.contracts.Strategy;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Cooperates on the first move, and defects if the opponent has defects on any of the previous three moves, else cooperates.
 */
public class HardTitForTatStrategy implements Strategy {

    private Queue<Boolean> previousOpponentMoves;
    private boolean isFirstTurn;

    public HardTitForTatStrategy() {
        this.isFirstTurn = true;
        this.previousOpponentMoves = new ArrayDeque<>();
    }

    @Override
    public boolean makeMove() {
        if (this.isFirstTurn) {
            this.isFirstTurn = false;
            return true;
        }

        return !this.previousOpponentMoves.contains(false);

    }

    @Override
    public void updateOpponentMove(boolean opponentMove) {
        if (this.previousOpponentMoves.size() == 3) {
            this.previousOpponentMoves.poll();
        }
        this.previousOpponentMoves.add(opponentMove);
    }

    @Override
    public void resetStrategy() {
        this.isFirstTurn = true;
        this.previousOpponentMoves = new ArrayDeque<>();
    }
}
