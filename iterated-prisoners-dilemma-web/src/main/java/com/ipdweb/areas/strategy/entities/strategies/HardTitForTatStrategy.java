package com.ipdweb.areas.strategy.entities.strategies;

import com.ipdweb.areas.strategy.entities.StrategyImpl;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Cooperates on the first move, and defects if the opponent has defects on any of the previous three moves, else cooperates.
 */
@Entity
@DiscriminatorValue(value="HardTitForTatStrategy")
public class HardTitForTatStrategy extends StrategyImpl {
    private static final String NAME = "HardTitForTat";
    private static final String DESCRIPTION = "Cooperates on the first move, and defects if the opponent has defects on any of the previous three moves, else cooperates.";

    @Transient
    private Queue<Boolean> previousOpponentMoves;
    @Transient
    private boolean isFirstTurn;

    public HardTitForTatStrategy() {
        super(NAME,DESCRIPTION);
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
