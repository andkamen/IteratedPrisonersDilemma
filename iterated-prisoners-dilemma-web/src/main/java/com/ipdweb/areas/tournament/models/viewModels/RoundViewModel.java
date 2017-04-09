package com.ipdweb.areas.tournament.models.viewModels;

public class RoundViewModel {

    private int turnNumber;

    private boolean stratAMove;

    private boolean stratBMove;

    public RoundViewModel() {
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    public boolean isStratAMove() {
        return stratAMove;
    }

    public void setStratAMove(boolean stratAMove) {
        this.stratAMove = stratAMove;
    }

    public boolean isStratBMove() {
        return stratBMove;
    }

    public void setStratBMove(boolean stratBMove) {
        this.stratBMove = stratBMove;
    }
}
