package com.ipdweb.areas.tournament.models.bindingModels;

import com.ipdweb.areas.tournament.customValidations.FilterOnlySelected;

import javax.validation.constraints.Size;

@FilterOnlySelected
public class SelectMatchUpResultsBindingModel {

    private long id;

    private boolean filterOnlySelected;

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

    public boolean isFilterOnlySelected() {
        return filterOnlySelected;
    }

    public void setFilterOnlySelected(boolean filterOnlySelected) {
        this.filterOnlySelected = filterOnlySelected;
    }
}
