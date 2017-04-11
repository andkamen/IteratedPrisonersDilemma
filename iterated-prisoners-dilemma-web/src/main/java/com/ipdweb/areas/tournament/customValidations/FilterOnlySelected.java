package com.ipdweb.areas.tournament.customValidations;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = FilterOnlySelectedValidator.class)
public @interface FilterOnlySelected {

    String message() default "Select at least 1 strategy if no filter is applied. At least 2 strategies if with filter.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
