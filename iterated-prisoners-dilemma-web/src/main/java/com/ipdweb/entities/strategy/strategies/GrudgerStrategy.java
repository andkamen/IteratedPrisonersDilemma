package com.ipdweb.entities.strategy.strategies;

import com.ipdweb.entities.strategy.StrategyImpl;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * This Strategy will cooperate until it's "betrayed". After that it will always be selfish.
 */
@Entity
@DiscriminatorValue(value="GrudgerStrategy")
public class GrudgerStrategy extends StrategyImpl {

    private static final String NAME = "Grudger";
    private static final String DESCRIPTION = "This Strategy will cooperate until it's \"betrayed\". After that it will always be selfish.";

    @Transient
    private boolean wasBetrayed;

    public GrudgerStrategy() {
        super(NAME,DESCRIPTION);
        wasBetrayed = false;
    }

    @Override
    public boolean makeMove() {
        return !wasBetrayed;
    }

    @Override
    public void updateOpponentMove(boolean opponentMove) {
        if (!opponentMove) {
            this.wasBetrayed = true;
        }
    }

    @Override
    public void resetStrategy() {
        this.wasBetrayed = false;
    }
}
