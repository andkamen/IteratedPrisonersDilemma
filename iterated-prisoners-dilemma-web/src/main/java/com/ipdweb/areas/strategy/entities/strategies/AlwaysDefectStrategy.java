package com.ipdweb.areas.strategy.entities.strategies;

import com.ipdweb.areas.strategy.entities.StrategyImpl;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * This Strategy will always be selfish.
 */
@Entity
@DiscriminatorValue(value="AlwaysDefectStrategy")
public class AlwaysDefectStrategy extends StrategyImpl {

    private static final String NAME = "AlwaysDefect";
    private static final String DESCRIPTION = " This Strategy will always be selfish.";

    public AlwaysDefectStrategy() {
        super(NAME,DESCRIPTION);
    }

    @Override
    public boolean makeMove() {
        //always selfish
        return false;
    }

    @Override
    public void updateOpponentMove(boolean opponentMove) {
        //doesn't need opponent move info because its always selfish
    }

    @Override
    public void resetStrategy() {
        //nothing changes in the strat. Nothing to reset
    }
}
