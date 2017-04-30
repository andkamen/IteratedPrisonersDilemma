package com.ipdweb.areas.common.customValidations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = StrategyMapInputValidator.class)
public @interface ValidateStrategyMapInput {

    String message() default "No null or negative values allowed. Must have at least 2 strategies total";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
