package com.yallaIg.yallaIg_backend.validator.phone;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
public @interface PhoneNumber {

    String message() default "Phone Number is not correct";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

