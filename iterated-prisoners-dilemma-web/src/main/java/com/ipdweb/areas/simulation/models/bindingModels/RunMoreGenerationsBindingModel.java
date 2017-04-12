package com.ipdweb.areas.simulation.models.bindingModels;

import javax.validation.constraints.Min;

public class RunMoreGenerationsBindingModel {

    private long id;

    @Min(value = 1, message = "Must run at least 1 generation.")
    private int generationCount;

    public RunMoreGenerationsBindingModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getGenerationCount() {
        return generationCount;
    }

    public void setGenerationCount(int generationCount) {
        this.generationCount = generationCount;
    }
}
