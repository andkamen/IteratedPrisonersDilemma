package com.ipdweb.areas.tournament.models.viewModels;

import java.util.LinkedHashSet;
import java.util.Set;

public class MatchUpResultViewModel {

    private String stratAName;

    private String stratBName;

    private int stratAScore;

    private int stratBScore;

    private Set<RoundViewModel> rounds;

    public MatchUpResultViewModel() {
        this.rounds = new LinkedHashSet<>();
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

    public Set<RoundViewModel> getRounds() {
        return rounds;
    }

    public void setRounds(Set<RoundViewModel> rounds) {
        this.rounds = rounds;
    }
}
