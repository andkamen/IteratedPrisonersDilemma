package com.ipdweb.areas.tournament.models.bindingModels;

import javax.validation.constraints.Size;

public class SelectMatchUpResultsBindingModel {

    private long id;

    @Size(min = 1, message = "You must select at least 1 strategy to see detailed match up results")
    private String[] strategyMatchUps;

    public SelectMatchUpResultsBindingModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String[] getStrategyMatchUps() {
        return strategyMatchUps;
    }

    public void setStrategyMatchUps(String[] strategyMatchUps) {
        this.strategyMatchUps = strategyMatchUps;
    }
}
