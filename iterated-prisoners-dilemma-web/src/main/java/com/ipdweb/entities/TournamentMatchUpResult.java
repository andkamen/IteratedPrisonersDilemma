package com.ipdweb.entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "tournament_match_up_results")
public class TournamentMatchUpResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "strat_A_name")
    private String stratAName;

    @Column(name = "strat_B_name")
    private String stratBName;

    @Column(name = "strat_A_score")
    private int stratAScore;

    @Column(name = "strat_B_score")
    private int stratBScore;

    @ManyToOne()
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @OneToMany(mappedBy = "tournamentMatchUpResult", cascade = CascadeType.ALL)
    private Set<Round> rounds;

    public TournamentMatchUpResult(String stratAName, String stratBName, Tournament tournament) {
        this.stratAName = stratAName;
        this.stratBName = stratBName;
        this.tournament = tournament;
        this.rounds = new LinkedHashSet<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Set<Round> getRounds() {
        return rounds;
    }

    public void setRounds(Set<Round> rounds) {
        this.rounds = rounds;
    }

    public void addRound(Round round) {
        this.rounds.add(round);
    }
}
