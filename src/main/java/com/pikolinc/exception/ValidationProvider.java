package com.pikolinc.exception;

import jakarta.validation.*;

import java.util.Set;

public class ValidationProvider {
    private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = validatorFactory.getValidator();

    public static <T> void validate(T Object) {
        Set<ConstraintViolation<T>> violations = validator.validate(Object);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
