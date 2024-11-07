package com.example.hw17resilience4j.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = NonNullElementsConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface NonNullElements {
    String message() default "The collection cannot contain null elements.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
