package com.pikolinc.utils;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OfferStatusEnumValidator.class)
public @interface ValidOfferStatus {
    String message() default "Invalid OfferStatus, OfferStatus must be ACCEPTED, REJECTED or PENDING";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
