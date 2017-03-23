package com.simulation.models.strategies;

import com.simulation.contracts.Strategy;

/**
 * Like Grudger except that the opponent is punished with D,D,D,D,C,C.
 */
public class SoftGrudgerStrategy implements Strategy {

    private boolean punishmentInProgress;
    private int currentPunishmentTurn;
    private int punishmentDuration;
    private boolean[] punishmentRoutine = {false, false, false, false, true, true};

    public SoftGrudgerStrategy() {
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
