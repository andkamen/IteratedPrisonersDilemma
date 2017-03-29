package com.ipdweb.areas.strategy.entities.strategies;

import com.ipdweb.areas.strategy.entities.StrategyImpl;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * Same as Tit for Tat, except that it defects on the first move
 */
@Entity
@DiscriminatorValue(value="SuspiciousTitForTatStrategy")
public class SuspiciousTitForTatStrategy extends StrategyImpl {

    private static final String NAME = "SuspiciousTitForTat";
    private static final String DESCRIPTION = "Same as Tit for Tat, except that it defects on the first move.";

    @Transient
    private boolean isFirstMove;
    @Transient
    private boolean lastOpponentMove;

    public SuspiciousTitForTatStrategy() {
        super(NAME,DESCRIPTION);
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
