package com.ipdweb.areas.simulation.models.bindingModels;

import com.ipdweb.areas.common.customValidations.ValidateStrategyMapInput;
import com.ipdweb.areas.common.interfaces.StrategyMapModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashMap;
import java.util.Map;

@ValidateStrategyMapInput
public class CreateSimulationBindingModel implements StrategyMapModel {

    @Size(min = 4, message = "Simulation name must be at least 4 chars long")
    private String name;

    @NotNull
    @Min(value = 0, message = "Generation run count cannot be negative")
    private Integer generationCount;

    private Map<String, Integer> strategies;

    public CreateSimulationBindingModel() {
        this.strategies = new LinkedHashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGenerationCount() {
        return generationCount;
    }

    public void setGenerationCount(Integer generationCount) {
        this.generationCount = generationCount;
    }

    public Map<String, Integer> getStrategies() {
        return strategies;
    }

    public void setStrategies(Map<String, Integer> strategies) {
        this.strategies = strategies;
    }
}
