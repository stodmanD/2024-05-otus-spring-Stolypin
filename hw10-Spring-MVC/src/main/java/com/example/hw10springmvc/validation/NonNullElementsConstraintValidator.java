package com.example.hw10springmvc.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.CollectionUtils;

import java.util.Set;

public class NonNullElementsConstraintValidator implements ConstraintValidator<NonNullElements, Set<?>> {
    @Override
    public boolean isValid(Set<?> value, ConstraintValidatorContext context) {
        return !CollectionUtils.containsInstance(value, null);
    }
}
