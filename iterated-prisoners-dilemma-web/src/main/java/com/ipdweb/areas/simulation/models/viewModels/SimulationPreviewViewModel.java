package com.ipdweb.areas.simulation.models.viewModels;

import java.util.Map;

public class SimulationPreviewViewModel {

    private long id;

    private String name;

    private Map<String, Integer> strategyCount;

    private int generationCount;

    public SimulationPreviewViewModel() {
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

    public Map<String, Integer> getStrategyCount() {
        return strategyCount;
    }

    public void setStrategyCount(Map<String, Integer> strategyCount) {
        this.strategyCount = strategyCount;
    }

    public int getGenerationCount() {
        return generationCount;
    }

    public void setGenerationCount(int generationCount) {
        this.generationCount = generationCount;
    }
}
