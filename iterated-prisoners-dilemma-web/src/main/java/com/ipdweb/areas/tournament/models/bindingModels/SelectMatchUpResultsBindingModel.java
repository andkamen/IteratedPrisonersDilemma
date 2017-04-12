package com.ipdweb.areas.tournament.models.bindingModels;

import com.ipdweb.areas.tournament.customValidations.FilterOnlySelected;

@FilterOnlySelected
public class SelectMatchUpResultsBindingModel {

    private long id;

    private boolean dualFilter;

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

    public boolean isDualFilter() {
        return dualFilter;
    }

    public void setDualFilter(boolean dualFilter) {
        this.dualFilter = dualFilter;
    }
}
