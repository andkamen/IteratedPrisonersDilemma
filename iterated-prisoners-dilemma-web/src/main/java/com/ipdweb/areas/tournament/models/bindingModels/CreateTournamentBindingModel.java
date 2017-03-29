package com.ipdweb.areas.tournament.models.bindingModels;

import com.ipdweb.areas.tournament.customValidations.ValidateStrategyMapInput;

import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;

@ValidateStrategyMapInput
public class CreateTournamentBindingModel {

    @Size(min = 4,message = "Tournament name must be at least 4 chars long")
    private String name;

    private Map<String,Integer> strategies;

    public CreateTournamentBindingModel() {
        this.strategies = new HashMap<>();
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
