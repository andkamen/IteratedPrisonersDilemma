package com.ipdweb.areas.strategy.entities.strategies;

import com.ipdweb.areas.strategy.entities.StrategyImpl;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * Cooperates on the first move, and cooperates as long as the opponent cooperates.
 * After the first defection of the other player, it defects one time and cooperates two times;
 * After the nth defection it reacts with n consecutive defections and then calms down its opponent with two cooperations.
 */
@Entity
@DiscriminatorValue(value="GradualStrategy")
public class GradualStrategy extends StrategyImpl {
    private static final String NAME = "Gradual";
    private static final String DESCRIPTION = "Cooperates on the first move, and cooperates as long as the opponent cooperates. After the first defection of the other player, it defects one time and cooperates two times;\n After the nth defection it reacts with n consecutive defections and then calms down its opponent with two cooperations.";

    @Transient
    private int maxDefectionCount;
    @Transient
    private boolean punishmentInProgress;
    @Transient
    private int currentPunishmentTurn;
    @Transient
    private int currentPunishmentDuration;

    public GradualStrategy() {
        super(NAME,DESCRIPTION);
        this.punishmentInProgress = false;
        this.maxDefectionCount = 1;
        this.currentPunishmentTurn = 0;
        this.currentPunishmentDuration = 0;
    }

    @Override
    public boolean makeMove() {
        if (this.punishmentInProgress && this.currentPunishmentTurn < this.currentPunishmentDuration - 2) {
            this.currentPunishmentTurn++;
            return false;
        } else if (this.punishmentInProgress && this.currentPunishmentTurn < this.currentPunishmentDuration) {
            this.currentPunishmentTurn++;
            if (this.currentPunishmentTurn == this.currentPunishmentDuration) {
                this.punishmentInProgress = false;
            }
            return true;
        }
        return true;
    }

    @Override
    public void updateOpponentMove(boolean opponentMove) {
        if (!opponentMove && !this.punishmentInProgress) {
            this.punishmentInProgress = true;
            this.currentPunishmentDuration = this.maxDefectionCount + 2;
            this.currentPunishmentTurn = 0;
            this.maxDefectionCount++;
        }
    }

    @Override
    public void resetStrategy() {
        this.punishmentInProgress = false;
        this.maxDefectionCount = 1;
        this.currentPunishmentTurn = 0;
        this.currentPunishmentDuration = 0;
    }
}
