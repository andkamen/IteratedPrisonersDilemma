package com.ipdweb.areas.tournament.customValidations;

import com.ipdweb.areas.tournament.models.bindingModels.SelectMatchUpResultsBindingModel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FilterOnlySelectedValidator implements ConstraintValidator<FilterOnlySelected, Object> {
    @Override
    public void initialize(FilterOnlySelected constraint) {
    }

    @Override
    public boolean isValid(Object selectMatchUpResults, ConstraintValidatorContext context) {
        if (selectMatchUpResults instanceof SelectMatchUpResultsBindingModel) {
            if (((SelectMatchUpResultsBindingModel) selectMatchUpResults).isFilterOnlySelected()) {
                if (((SelectMatchUpResultsBindingModel) selectMatchUpResults).getStrategyMatchUps().length < 2) {
                    return false;
                }
            }else{
                if (((SelectMatchUpResultsBindingModel) selectMatchUpResults).getStrategyMatchUps().length < 1) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }
}
