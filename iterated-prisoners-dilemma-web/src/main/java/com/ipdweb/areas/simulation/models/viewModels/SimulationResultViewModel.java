package com.ipdweb.areas.simulation.models.viewModels;

import java.util.ArrayList;
import java.util.List;

public class SimulationResultViewModel {

    private String id;

    private String name;

    private int[][] strategyScores;

    private List<String> strategyNames;


    private int generationCount;

    public SimulationResultViewModel() {
        this.strategyNames = new ArrayList<>();
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


    public int getGenerationCount() {
        return generationCount;
    }

    public void setGenerationCount(int generationCount) {
        this.generationCount = generationCount;
    }
}
