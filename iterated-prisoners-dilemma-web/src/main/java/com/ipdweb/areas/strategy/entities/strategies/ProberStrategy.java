package com.ipdweb.areas.strategy.entities.strategies;

import com.ipdweb.areas.strategy.entities.StrategyImpl;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * Starts with D,C,C and then defects if the opponent has cooperated in the second and third move;
 * otherwise, it plays Tit For Tat.
 */
@Entity
@DiscriminatorValue(value="ProberStrategy")
public class ProberStrategy extends StrategyImpl {
    private static final String NAME = "Prober";
    private static final String DESCRIPTION = "Starts with D,C,C and then defects if the opponent has cooperated in the second and third move; otherwise, it plays Tit For Tat.";

    @Transient
    private boolean lastOpponentMove;
    @Transient
    private boolean[] openingMoves = {false, true, true};
    @Transient
    private int openingTurn;
    @Transient
    private boolean[] opponentFirstMoves;
    @Transient
    private boolean shouldAlwaysDefect;

    public ProberStrategy() {
        super(NAME,DESCRIPTION);
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
