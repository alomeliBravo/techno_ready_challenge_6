package com.pikolinc.utils;

import com.pikolinc.enums.OfferStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.EnumSet;

public class OfferStatusEnumValidator implements ConstraintValidator<ValidOfferStatus, OfferStatus> {
    @Override
    public boolean isValid(OfferStatus value, ConstraintValidatorContext context){
        return value == null || EnumSet.allOf(OfferStatus.class).contains(value);
    }
}
