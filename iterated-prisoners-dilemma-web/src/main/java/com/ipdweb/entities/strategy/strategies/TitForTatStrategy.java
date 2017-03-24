package com.ipdweb.entities.strategy.strategies;


import com.ipdweb.entities.strategy.StrategyImpl;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * Cooperates on the first move, then copies the opponent’s last move.
 */
@Entity
@DiscriminatorValue(value="TitForTatStrategy")
public class TitForTatStrategy extends StrategyImpl {

    private static final String NAME = "TitForTat";
    private static final String DESCRIPTION = "Cooperates on the first move, then copies the opponent’s last move.";

    @Transient
    private boolean isFirstMove;
    @Transient
    private boolean lastOpponentMove;

    public TitForTatStrategy() {
        super(NAME,DESCRIPTION);
        this.isFirstMove = true;
    }

    @Override
    public boolean makeMove() {
        if (this.isFirstMove) {
            this.isFirstMove = false;
            return true;
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
