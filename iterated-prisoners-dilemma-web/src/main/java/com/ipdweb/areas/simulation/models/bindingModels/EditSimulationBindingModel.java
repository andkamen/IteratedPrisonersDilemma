package com.ipdweb.areas.simulation.models.bindingModels;

import com.ipdweb.areas.common.customValidations.ValidateStrategyMapInput;
import com.ipdweb.areas.common.interfaces.StrategyMapModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.LinkedHashMap;
import java.util.Map;

@ValidateStrategyMapInput
public class EditSimulationBindingModel implements StrategyMapModel {

    private long id;

    @Size(min = 4, message = "Tournament name must be at least 4 chars long")
    private String name;

    @Min(value = 0, message = "Generation run count cannot be negative")
    private int generationCount;

    private Map<String, Integer> strategies;

    public EditSimulationBindingModel() {
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

    public int getGenerationCount() {
        return generationCount;
    }

    public void setGenerationCount(int generationCount) {
        this.generationCount = generationCount;
    }
}
