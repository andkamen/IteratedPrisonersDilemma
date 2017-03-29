package com.ipdweb.areas.strategy.entities.strategies;

import com.ipdweb.areas.strategy.entities.StrategyImpl;

/**
 * Defects on the first move, and defects if the number of defections of the opponent
 * is greater than or equal to the number of times it has cooperated, else cooperates.
 */
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue(value="HardMajorityStrategy")
public class HardMajorityStrategy extends StrategyImpl {
    private static final String NAME = "HardMajority";
    private static final String DESCRIPTION = "Defects on the first move, and defects if the number of defections of the opponent is greater than or equal to the number of times it has cooperated, else cooperates.";

    @Transient
    private int opponentDefectCount;
    @Transient
    private int opponentCooperateCount;

    public HardMajorityStrategy() {
        super(NAME,DESCRIPTION);
        this.opponentCooperateCount = 0;
        this.opponentDefectCount = 0;
    }

    @Override
    public boolean makeMove() {
        if (this.opponentDefectCount >= opponentCooperateCount) {
            return false;
        }
        return true;
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
