package com.example.ResortMenager.validations;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateOrderValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateOrder {
    String message() default "Departure date must be after arrival date.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
