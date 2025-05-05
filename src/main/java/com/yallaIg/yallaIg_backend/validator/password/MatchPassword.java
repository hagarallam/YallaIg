package com.yallaIg.yallaIg_backend.validator.password;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.TYPE })
@Documented
@Constraint(validatedBy = PasswordMatchValidator.class)
public @interface MatchPassword {
    String message() default "Passwords don't match";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
