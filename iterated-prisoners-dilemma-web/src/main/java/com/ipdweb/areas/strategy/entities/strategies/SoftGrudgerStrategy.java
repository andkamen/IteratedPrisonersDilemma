package com.ipdweb.areas.strategy.entities.strategies;

import com.ipdweb.areas.strategy.entities.StrategyImpl;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * Like Grudger except that the opponent is punished with D,D,D,D,C,C.
 */
@Entity
@DiscriminatorValue(value="SoftGrudgerStrategy")
public class SoftGrudgerStrategy extends StrategyImpl {

    private static final String NAME = "SoftGrudger";
    private static final String DESCRIPTION = "Like Grudger except that the opponent is punished with D,D,D,D,C,C.";

    @Transient
    private boolean punishmentInProgress;
    @Transient
    private int currentPunishmentTurn;
    @Transient
    private int punishmentDuration;
    @Transient
    private boolean[] punishmentRoutine = {false, false, false, false, true, true};

    public SoftGrudgerStrategy() {
        super(NAME,DESCRIPTION);
        this.punishmentInProgress = false;
        this.currentPunishmentTurn = 0;
        this.punishmentDuration = punishmentRoutine.length;
    }

    @Override
    public boolean makeMove() {
        if (punishmentInProgress) {
            boolean move = this.punishmentRoutine[this.currentPunishmentTurn++];
            if (this.currentPunishmentTurn == this.punishmentDuration) {
                this.punishmentInProgress = false;
            }
            return move;
        }
        return true;
    }

    @Override
    public void updateOpponentMove(boolean opponentMove) {
        if (!opponentMove && !this.punishmentInProgress) {
            this.punishmentInProgress = true;
            this.currentPunishmentTurn = 0;
        }
    }

    @Override
    public void resetStrategy() {
        this.punishmentInProgress = false;
        this.currentPunishmentTurn = 0;
    }
}
