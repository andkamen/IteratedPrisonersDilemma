package com.ipdweb.areas.strategy.entities.strategies;


import com.ipdweb.areas.strategy.entities.StrategyImpl;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * This Strategy will always cooperate.
 */

@Entity
@DiscriminatorValue(value="AlwaysCooperateStrategy")
public class AlwaysCooperateStrategy extends StrategyImpl {

    private static final String NAME = "AlwaysCooperate";
    private static final String DESCRIPTION = "This Strategy will always cooperate.";

    public AlwaysCooperateStrategy() {
        super(NAME,DESCRIPTION);
    }

    @Override
    public boolean makeMove() {
        //always cooperates
        return true;
    }

    @Override
    public void updateOpponentMove(boolean opponentMove) {
        //doesn't need opponent move info because it always cooperates
    }

    @Override
    public void resetStrategy() {
        //nothing changes in the strat. Nothing to reset
    }
}
