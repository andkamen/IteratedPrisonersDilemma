package com.ipdweb.entities.strategy.strategies;

import com.ipdweb.entities.strategy.StrategyImpl;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * Cooperates on the first move, and defects only when the opponent defects two times.
 */
@Entity
@DiscriminatorValue(value="TitForTwoTatsStrategy")
public class TitForTwoTatsStrategy extends StrategyImpl {

    private static final String NAME = "TitForTwoTats";
    private static final String DESCRIPTION = "Cooperates on the first move, and defects only when the opponent defects two times.";

    @Transient
    private int opponentConsecutiveDefectCount;

    public TitForTwoTatsStrategy() {
        super(NAME,DESCRIPTION);
        this.opponentConsecutiveDefectCount = 0;
    }

    @Override
    public boolean makeMove() {

        if (this.opponentConsecutiveDefectCount > 1) {
            return false;
        }

        return true;
    }

    @Override
    public void updateOpponentMove(boolean opponentMove) {
        if (!opponentMove) {
            this.opponentConsecutiveDefectCount++;
        } else {
            this.opponentConsecutiveDefectCount = 0;
        }
    }

    @Override
    public void resetStrategy() {
        this.opponentConsecutiveDefectCount = 0;
    }
}
