package com.ipdweb.areas.tournament.customValidations;


import com.ipdweb.areas.tournament.models.bindingModels.interfaces.StrategyMapModel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

//TODO dumb name
public class ValidateStrategyMapInputValidator implements ConstraintValidator<ValidateStrategyMapInput, Object> {
    @Override
    public void initialize(ValidateStrategyMapInput validateStrategyMapInput) {

    }

    @Override
    public boolean isValid(Object strategyMapModel, ConstraintValidatorContext constraintValidatorContext) {
        if (strategyMapModel instanceof StrategyMapModel) {
            int totalCount = 0;
            for (Map.Entry<String, Integer> entry : ((StrategyMapModel) strategyMapModel).getStrategies().entrySet()) {

                if (entry.getValue() == null || entry.getValue() < 0) {
                    return false;
                }

                totalCount += entry.getValue();
            }

            if (totalCount < 2) {
                return false;
            }
            return true;
        }

        return false;
    }
}
