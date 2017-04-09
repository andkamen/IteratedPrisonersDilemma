package com.ipdweb.areas.tournament.models.bindingModels;

import javax.validation.constraints.Size;

public class SelectMatchUpResultsBindingModel {

    @Size(min = 1, message = "You must select at least 1 strategy to see detailed match up results")
    private String[] strategyMatchUps;

    public SelectMatchUpResultsBindingModel() {
    }

    public String[] getStrategyMatchUps() {
        return strategyMatchUps;
    }

    public void setStrategyMatchUps(String[] strategyMatchUps) {
        this.strategyMatchUps = strategyMatchUps;
    }
}
