package com.yallaIg.yallaIg_backend.validator.phone;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        return Objects.nonNull(phoneNumber) && phoneNumber.matches("[0-9]+")
                && (phoneNumber.length() > 8)
                && (phoneNumber.length() < 14);

    }
}
