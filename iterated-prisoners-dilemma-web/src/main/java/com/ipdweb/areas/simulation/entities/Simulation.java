package com.ipdweb.areas.simulation.entities;

import com.ipdweb.areas.strategy.entities.StrategyImpl;
import com.ipdweb.areas.strategy.factories.StrategyFactory;
import com.ipdweb.areas.strategy.factories.StrategyFactoryImpl;
import com.ipdweb.areas.user.entities.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "simulations")
public class Simulation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    private String name;

    @OneToMany(mappedBy = "simulation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Generation> generations;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @Transient
    private StrategyFactory strategyFactory;

    public Simulation(String name) {
        this.name = name;
        this.generations = new ArrayList<>();
        this.generations.add(new Generation(this, name + "_gen_" + generations.size()));
        this.strategyFactory = new StrategyFactoryImpl();
    }

    public Simulation() {
        this.generations = new ArrayList<>();
        this.strategyFactory = new StrategyFactoryImpl();
    }

    public void addStrategy(StrategyImpl strategy) {
        this.generations.get(0).addStrategy(strategy);
    }

    public void removeStrategy(String name) {
        this.generations.get(0).removeStrategy(name);
    }

    public void run(int generationCount) {
        Generation nextGeneration = null;

        for (int i = 0; i < generationCount; i++) {
            this.generations.get(this.generations.size() - 1).playOut();

            nextGeneration = setUpNextGeneration(this.generations.get(this.generations.size() - 1));

            this.generations.add(nextGeneration);
        }
    }

    /// http://stackoverflow.com/questions/13483430/how-to-make-rounded-percentages-add-up-to-100
    // the answer by paxdiablo has the algorithm used for rounding up the percentages with least amount of error.
    private Generation setUpNextGeneration(Generation currentGeneration) {
        Generation nextGeneration = new Generation(this, name + "_gen_" + generations.size());

        //Get the current gen info: Total places, all strats and their scores
        Map<String, Integer> strategyScores = currentGeneration.getStrategyScores();
        int nextGenPlaceCount = currentGeneration.getTotalStrategyCount();

        //the relation list will give us info about which element in the calc list corresponds to which strategy
        String[] strategyRelation = new String[nextGenPlaceCount + 1];
        strategyRelation[0] = "Placeholder";
        double[] strategyDistribution = new double[nextGenPlaceCount + 1];
        strategyDistribution[0] = 0d;

        double totalScore = 0;
        for (Integer strategyTypeTotalScore : strategyScores.values()) {
            totalScore += strategyTypeTotalScore;
        }

        //fill in the arrays with the strategy and their corresponding % of total score
        int tempIndex = 1;
        for (Map.Entry<String, Integer> strategyScoreEntry : strategyScores.entrySet()) {
            strategyRelation[tempIndex] = strategyScoreEntry.getKey();
            strategyDistribution[tempIndex++] = strategyScoreEntry.getValue() / totalScore;
        }

        strategyDistribution = calculateNextGenDistribution(nextGenPlaceCount, strategyDistribution);

        for (int i = 1; i < strategyRelation.length; i++) {
            String strategyName = strategyRelation[i];
            int strategyCount = (int) strategyDistribution[i];
            for (int j = 0; j < strategyCount; j++) {
                nextGeneration.addStrategy(this.strategyFactory.hackStrategy(strategyName, this.generations.get(0).getStrategies()));
            }
        }
        return nextGeneration;
    }

    private double[] calculateNextGenDistribution(int nextGenPlaceCount, double[] strategyDistribution) {
        //calculate how many spots strategy should take in next generation (as a fraction)
        for (int i = 1; i < strategyDistribution.length; i++) {
            strategyDistribution[i] *= nextGenPlaceCount;
        }

        //add up previous elements to each element
        for (int i = 1; i < strategyDistribution.length; i++) {
            strategyDistribution[i] += strategyDistribution[i - 1];
        }
        //round the results to nearest integer
        for (int i = 1; i < strategyDistribution.length; i++) {
            strategyDistribution[i] = (double) Math.round(strategyDistribution[i]);
        }

        //get final values for number of spots
        for (int i = strategyDistribution.length - 1; i > 0; i--) {
            strategyDistribution[i] -= strategyDistribution[i - 1];
        }

        return strategyDistribution;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Generation> getGenerations() {
        return generations;
    }

    public void setGenerations(List<Generation> generations) {
        this.generations = generations;
    }

    public int getGenerationCount() {
        return this.generations.size();
    }
}
