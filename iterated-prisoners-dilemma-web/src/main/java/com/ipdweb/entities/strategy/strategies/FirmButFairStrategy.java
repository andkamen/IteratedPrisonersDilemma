package com.ipdweb.entities.strategy.strategies;

import com.ipdweb.entities.strategy.StrategyImpl;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * Cooperates on the first move, and cooperates except after receiving a sucker payoff.
 */
@Entity
@DiscriminatorValue(value="FirmButFairStrategy")
public class FirmButFairStrategy extends StrategyImpl {

    private static final String NAME = "FirmButFair";
    private static final String DESCRIPTION = "Cooperates on the first move, and cooperates except after receiving a sucker payoff.";

    @Transient
    private boolean isFirstMove;
    @Transient
    private boolean lastOpponentMove;
    @Transient
    private boolean lastMove;

    public FirmButFairStrategy() {
        super(NAME,DESCRIPTION);
        this.isFirstMove = true;
    }

    @Override
    public boolean makeMove() {
        if (this.isFirstMove) {
            this.isFirstMove = false;

            this.lastMove = true;
            return true;
        }
        if (!this.lastOpponentMove && this.lastMove) {
            this.lastMove = false;
            return false;
        }

        this.lastMove = true;
        return true;
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
