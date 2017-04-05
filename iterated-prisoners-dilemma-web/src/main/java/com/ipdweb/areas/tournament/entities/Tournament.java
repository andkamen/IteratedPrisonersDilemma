package com.ipdweb.areas.tournament.entities;

import com.ipdweb.areas.strategy.entities.StrategyImpl;
import com.ipdweb.areas.strategy.entities.interfaces.Strategy;
import com.ipdweb.areas.user.entities.User;
import com.ipdweb.areas.utils.Constants;

import javax.persistence.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "tournaments")
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    private String name;

    @ManyToMany()
    @JoinTable(
            joinColumns = @JoinColumn(name = "tournament_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "strategy_id", referencedColumnName = "id"))
    private List<StrategyImpl> strategies;

    @Transient
    private List<Integer> strategyScores;

    //TODO read about orphanRemoval
    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<TournamentMatchUpResult> tournamentMatchUpResults;

    @Column(name = "round_count")
    private int roundCount;

    @Transient
    private Random rand;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    public Tournament() {
        this.strategyScores = new ArrayList<>();
        this.strategies = new ArrayList<>();
        this.tournamentMatchUpResults = new ArrayList<>();
        this.rand = new Random();

        setNewRoundCount();
    }

    //TODO disable level 1 caching ??
    //TODO inserting new instances of strategy in the List, causes it to throw TransientObjectException: object references an unsaved transient instance - save the transient instance before flushing: com.ipdweb.areas.strategy.entities.StrategyImpl]
    public void addStrategy(StrategyImpl strategy) {
//        try {
//            this.strategies.add(strategy.getClass().getConstructor().newInstance());
//        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
//            e.printStackTrace();
//        }
        this.strategies.add(strategy);

        this.strategyScores.add(0);
    }

    public void removeStrategy(String strategy) {
        boolean foundMatch = false;
        int indexToRemove = 0;

        for (int i = 0; i < strategies.size(); i++) {
            if (strategies.get(i).getName().equals(strategy)) {
                foundMatch = true;
                indexToRemove = i;
                break;
            }
        }

        if (foundMatch) {
            strategies.remove(indexToRemove);
            strategyScores.remove(indexToRemove);
        }
    }

    public void playOut() {
        Strategy stratA = null;
        Strategy stratB = null;
        int strategyScore;
        TournamentMatchUpResult tournamentMatchUpResult;

        for (int a = 0; a < this.strategies.size(); a++) {
            stratA = this.strategies.get(a);
            for (int b = 0; b < this.strategies.size(); b++) {
                if (b <= a) {
                    continue;
                }
                stratB = this.strategies.get(b);

                tournamentMatchUpResult = matchUp(stratA, stratB);
                this.tournamentMatchUpResults.add(tournamentMatchUpResult);

                strategyScore = this.strategyScores.get(a) + tournamentMatchUpResult.getStratAScore();
                this.strategyScores.set(a, strategyScore);

                strategyScore = this.strategyScores.get(b) + tournamentMatchUpResult.getStratBScore();
                this.strategyScores.set(b, strategyScore);
            }
        }
    }

    private TournamentMatchUpResult matchUp(Strategy stratA, Strategy stratB) {

        try {
            stratA = stratA.getClass().getConstructor().newInstance();
            stratB = stratB.getClass().getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        TournamentMatchUpResult tournamentMatchUpResult = new TournamentMatchUpResult(stratA.getName(), stratB.getName(), this);

        boolean stratAMove;
        boolean stratBMove;
        int[] roundResult; // 0 for stratA , 1 for stratB
        int[] matchUpResult = new int[2];

        stratA.resetStrategy();
        stratB.resetStrategy();

        for (int i = 0; i < this.roundCount; i++) {

            stratAMove = stratA.makeMove();
            stratBMove = stratB.makeMove();

            stratA.updateOpponentMove(stratBMove);
            stratB.updateOpponentMove(stratAMove);

            Round round = new Round(i, stratAMove, stratBMove, tournamentMatchUpResult);
            tournamentMatchUpResult.addRound(round);

            roundResult = determineRoundResult(stratAMove, stratBMove);
            matchUpResult[0] += roundResult[0];
            matchUpResult[1] += roundResult[1];
        }

        tournamentMatchUpResult.setStratAScore(matchUpResult[0]);
        tournamentMatchUpResult.setStratBScore(matchUpResult[1]);

        return tournamentMatchUpResult;
    }

    private int[] determineRoundResult(boolean stratAMove, boolean stratBMove) {
        int[] roundResult = new int[2];

        if (stratAMove && stratBMove) {
            //Both cooperate
            roundResult[0] = Constants.COOPERATE_COOPERATE_SCORE;
            roundResult[1] = Constants.COOPERATE_COOPERATE_SCORE;
        } else if (!stratAMove && !stratBMove) {
            //both defect
            roundResult[0] = Constants.DEFECT_DEFECT_SCORE;
            roundResult[1] = Constants.DEFECT_DEFECT_SCORE;
        } else if (!stratAMove) {
            //A defects B cooperates
            roundResult[0] = Constants.DEFECT_COOPERATE_SCORE;
            roundResult[1] = Constants.COOPERATE_DEFECT_SCORE;
        } else {
            //B defects A cooperates
            roundResult[0] = Constants.COOPERATE_DEFECT_SCORE;
            roundResult[1] = Constants.DEFECT_COOPERATE_SCORE;
        }
        return roundResult;
    }

    private void setNewRoundCount() {
        //Random number between A and B = rand((B-A)+1) + A
        int newRoundCount = this.rand.nextInt((Constants.MAX_TOURNAMENT_ROUND_COUNT - Constants.MIN_TOURNAMENT_ROUND_COUNT) + 1) + Constants.MIN_TOURNAMENT_ROUND_COUNT;
        this.setRoundCount(newRoundCount);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StrategyImpl> getStrategies() {
        return strategies;
    }

    public void setStrategies(List<StrategyImpl> strategies) {
        this.strategies = strategies;
    }

    public List<TournamentMatchUpResult> getTournamentMatchUpResults() {
        return tournamentMatchUpResults;
    }

    public void setTournamentMatchUpResults(List<TournamentMatchUpResult> tournamentMatchUpResults) {
        this.tournamentMatchUpResults = tournamentMatchUpResults;
    }

    public int getRoundCount() {
        return roundCount;
    }

    public void setRoundCount(int roundCount) {
        this.roundCount = roundCount;
    }

    public List<Integer> getStrategyScores() {
        return strategyScores;
    }

    public void setStrategyScores(List<Integer> strategyScores) {
        this.strategyScores = strategyScores;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
