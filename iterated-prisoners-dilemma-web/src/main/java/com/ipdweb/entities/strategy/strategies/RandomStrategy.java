package com.ipdweb.entities.strategy.strategies;


import com.ipdweb.entities.strategy.StrategyImpl;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Random;

/**
 * This Strategy will be random and unpredictable.
 */
@Entity
@DiscriminatorValue(value="RandomStrategy")
public class RandomStrategy extends StrategyImpl {

    private static final String NAME = "Random";
    private static final String DESCRIPTION = "This Strategy will be random and unpredictable.";

    @Transient
    private Random rand;

    public RandomStrategy() {
        super(NAME,DESCRIPTION);
        this.rand = new Random();
    }

    @Override
    public boolean makeMove() {
        return this.rand.nextBoolean();
    }

    @Override
    public void updateOpponentMove(boolean opponentMove) {
        //doesn't need opponent move info because its always random
    }

    @Override
    public void resetStrategy() {
        //nothing changes in the strat. Nothing to reset
    }
}
