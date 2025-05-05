package com.yallaIg.yallaIg_backend.validator.file;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target( {ElementType.FIELD,ElementType.METHOD,ElementType.PARAMETER })
@Documented
@Constraint(validatedBy = FileValidator.class)
public @interface ValidFile {
    String message() default "File is not Valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};


    String[] allowedTypes() default {}; // Allowed file types
    long maxSize() default Long.MAX_VALUE; // Maximum file size in bytes
    boolean notEmpty() default true; // Whether the file should not be empty
}
