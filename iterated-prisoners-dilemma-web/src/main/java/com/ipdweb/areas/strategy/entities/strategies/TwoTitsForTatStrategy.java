package com.ipdweb.areas.strategy.entities.strategies;

import com.ipdweb.areas.strategy.entities.StrategyImpl;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * Same as Tit for Tat except that it defects twice when the opponent defects.
 */
@Entity
@DiscriminatorValue(value="TwoTitsForTatStrategy")
public class TwoTitsForTatStrategy extends StrategyImpl {

    private static final String NAME = "TwoTitsForTat";
    private static final String DESCRIPTION = "Same as Tit for Tat except that it defects twice when the opponent defects.";

    @Transient
    private boolean isFirstMove;
    @Transient
    private int consecutivePunishMoves;

    public TwoTitsForTatStrategy() {
        super(NAME,DESCRIPTION);
        this.isFirstMove = true;
        this.consecutivePunishMoves = 0;
    }

    @Override
    public boolean makeMove() {
        if (this.isFirstMove) {
            this.isFirstMove = false;
            return true;
        }
        if (this.consecutivePunishMoves > 0) {
            this.consecutivePunishMoves--;
            return false;
        }
        return true;
    }

    @Override
    public void updateOpponentMove(boolean opponentMove) {
        if (!opponentMove) {
            this.consecutivePunishMoves = 2;
        }
    }

    @Override
    public void resetStrategy() {
        this.isFirstMove = true;
        this.consecutivePunishMoves = 0;
    }
}
