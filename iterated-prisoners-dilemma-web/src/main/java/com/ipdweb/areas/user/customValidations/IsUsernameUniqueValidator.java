package com.ipdweb.areas.user.customValidations;

import com.ipdweb.areas.user.services.BasicUserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsUsernameUniqueValidator implements ConstraintValidator<IsUsernameUnique, Object> {

    private BasicUserService userService;

    @Autowired
    public IsUsernameUniqueValidator(BasicUserService userService) {
        this.userService = userService;
    }

    public void initialize(IsUsernameUnique constraint) {
    }

    public boolean isValid(Object username, ConstraintValidatorContext context) {

        if (username instanceof String) {
            return this.userService.isUsernameAvailable(((String) username));
        }

        return false;
    }
}
