package com.ipdweb.entities;

import javax.persistence.*;

@Entity
@Table(name = "generation_match_up_results")
public class GenerationMatchUpResult {

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
    @JoinColumn(name = "generation_id")
    private Generation generation;

    public GenerationMatchUpResult(String stratAName, String stratBName, Generation generation) {
        this.stratAName = stratAName;
        this.stratBName = stratBName;
        this.generation = generation;
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

    public Generation getGeneration() {
        return generation;
    }

    public void setGeneration(Generation generation) {
        this.generation = generation;
    }
}
