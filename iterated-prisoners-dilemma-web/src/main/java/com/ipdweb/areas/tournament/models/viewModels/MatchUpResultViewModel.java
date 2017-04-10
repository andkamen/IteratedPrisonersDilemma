package com.ipdweb.areas.tournament.models.viewModels;

import java.util.ArrayList;
import java.util.List;

public class MatchUpResultViewModel {

    private String stratAName;

    private String stratBName;

    private int stratAScore;

    private int stratBScore;

    private List<RoundViewModel> rounds;

    public MatchUpResultViewModel() {
        this.rounds = new ArrayList<>();
    }

    public String getStratAName() {
        return stratAName;
    }

    public void setStratAName(String stratAName) {
        this.stratAName = stratAName;
    }

    public String getStratBName() {
        return stratBName;
    }

    public void setStratBName(String stratBName) {
        this.stratBName = stratBName;
    }

    public int getStratAScore() {
        return stratAScore;
    }

    public void setStratAScore(int stratAScore) {
        this.stratAScore = stratAScore;
    }

    public int getStratBScore() {
        return stratBScore;
    }

    public void setStratBScore(int stratBScore) {
        this.stratBScore = stratBScore;
    }

    public List<RoundViewModel> getRounds() {
        return rounds;
    }

    public void setRounds(List<RoundViewModel> rounds) {
        this.rounds = rounds;
    }
}
