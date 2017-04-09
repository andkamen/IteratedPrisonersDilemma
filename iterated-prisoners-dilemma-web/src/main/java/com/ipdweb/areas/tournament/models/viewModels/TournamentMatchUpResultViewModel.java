package com.ipdweb.areas.tournament.models.viewModels;

import java.util.LinkedHashSet;
import java.util.Set;

public class TournamentMatchUpResultViewModel {

    private String id;

    private String name;

    private int roundCount;

    private Set<MatchUpResultViewModel> matchUpResults;

    public TournamentMatchUpResultViewModel() {
        this.matchUpResults = new LinkedHashSet<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoundCount() {
        return roundCount;
    }

    public void setRoundCount(int roundCount) {
        this.roundCount = roundCount;
    }

    public Set<MatchUpResultViewModel> getMatchUpResults() {
        return matchUpResults;
    }

    public void setMatchUpResults(Set<MatchUpResultViewModel> matchUpResults) {
        this.matchUpResults = matchUpResults;
    }
}
