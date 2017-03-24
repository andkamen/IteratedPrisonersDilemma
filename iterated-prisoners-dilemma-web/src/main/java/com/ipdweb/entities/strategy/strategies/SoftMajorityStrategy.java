package com.ipdweb.entities.strategy.strategies;

import com.ipdweb.entities.strategy.StrategyImpl;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * Cooperates on the first move, and cooperates as long as the number of times the opponent has cooperated
 * is greater than or equal to the number of times it has defected, else it defects.
 */
@Entity
@DiscriminatorValue(value="SoftMajorityStrategy")
public class SoftMajorityStrategy extends StrategyImpl {

    private static final String NAME = "SoftMajority";
    private static final String DESCRIPTION = "Cooperates on the first move, and cooperates as long as the number of times the opponent has cooperated is greater than or equal to the number of times it has defected, else it defects.";

    @Transient
    private int opponentDefectCount;
    @Transient
    private int opponentCooperateCount;

    public SoftMajorityStrategy() {
        super(NAME,DESCRIPTION);
        this.opponentCooperateCount = 0;
        this.opponentDefectCount = 0;
    }

    @Override
    public boolean makeMove() {
        if (this.opponentCooperateCount >= opponentDefectCount) {
            return true;
        }
        return false;
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
