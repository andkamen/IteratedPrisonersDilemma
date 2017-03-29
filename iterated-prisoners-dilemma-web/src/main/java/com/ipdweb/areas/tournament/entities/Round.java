package com.ipdweb.areas.tournament.entities;

import javax.persistence.*;


@Entity
@Table(name = "rounds")
public class Round {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    private int turnNumber;

    @Column(name = "strat_A_move")
    private boolean stratAMove;

    @Column(name = "strat_B_move")
    private boolean stratBMove;

    @ManyToOne()
    @JoinColumn(name = "tournament_match_up_result_id")
    private TournamentMatchUpResult tournamentMatchUpResult;

    public Round(int turnNumber, boolean stratAMove, boolean stratBMove, TournamentMatchUpResult tournamentMatchUpResult) {
        this.turnNumber = turnNumber;
        this.stratAMove = stratAMove;
        this.stratBMove = stratBMove;
        this.tournamentMatchUpResult = tournamentMatchUpResult;
    }

    public Round() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    public boolean getStratAMove() {
        return stratAMove;
    }

    public void setStratAMove(boolean stratAMove) {
        this.stratAMove = stratAMove;
    }

    public boolean getStratBMove() {
        return stratBMove;
    }

    public void setStratBMove(boolean stratBMove) {
        this.stratBMove = stratBMove;
    }

    public TournamentMatchUpResult getTournamentMatchUpResult() {
        return tournamentMatchUpResult;
    }

    public void setTournamentMatchUpResult(TournamentMatchUpResult tournamentMatchUpResult) {
        this.tournamentMatchUpResult = tournamentMatchUpResult;
    }
}
