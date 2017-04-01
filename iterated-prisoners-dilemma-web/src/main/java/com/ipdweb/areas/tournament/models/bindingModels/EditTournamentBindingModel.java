package com.ipdweb.areas.tournament.models.bindingModels;

import com.ipdweb.areas.customValidations.ValidateStrategyMapInput;
import com.ipdweb.areas.interfaces.StrategyMapModel;

import javax.validation.constraints.Size;
import java.util.LinkedHashMap;
import java.util.Map;

@ValidateStrategyMapInput
public class EditTournamentBindingModel implements StrategyMapModel {

    private long id;

    @Size(min = 4, message = "Tournament name must be at least 4 chars long")
    private String name;

    private Map<String, Integer> strategies;

    public EditTournamentBindingModel() {
        this.strategies = new LinkedHashMap<>();
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

    public Map<String, Integer> getStrategies() {
        return strategies;
    }

    public void setStrategies(Map<String, Integer> strategies) {
        this.strategies = strategies;
    }
}
