package com.ipdweb.areas.tournament.models.bindingModels;

import com.ipdweb.areas.common.customValidations.ValidateStrategyMapInput;
import com.ipdweb.areas.common.interfaces.StrategyMapModel;

import javax.validation.constraints.Size;
import java.util.LinkedHashMap;
import java.util.Map;

@ValidateStrategyMapInput
public class CreateTournamentBindingModel implements StrategyMapModel {

    @Size(min = 4,message = "Tournament name must be at least 4 chars long")
    private String name;

    private Map<String,Integer> strategies;

    public CreateTournamentBindingModel() {
        this.strategies = new LinkedHashMap<>();
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
