package com.yallaIg.yallaIg_backend.validator.file;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;


public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {


    private boolean notEmpty;
    private long maxSize;
    private List<String> allowedTypes;

    @Override
    public void initialize(ValidFile constraintAnnotation) {
        allowedTypes = List.of(constraintAnnotation.allowedTypes());
        maxSize = constraintAnnotation.maxSize();
        notEmpty = constraintAnnotation.notEmpty();
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {

        if(Objects.isNull(multipartFile) || multipartFile.isEmpty())
            return !notEmpty; // allow null image

        if (!isFileSizeValid(multipartFile.getSize(), constraintValidatorContext))
            return false;

        if(!isValidFormat(multipartFile.getContentType(),constraintValidatorContext))
            return false;

        return true;
    }

    private boolean isFileSizeValid(Long fileSize, ConstraintValidatorContext constraintValidatorContext) {
        if (fileSize > maxSize) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("File size exceeds the maximum limit").addConstraintViolation();
            return false;
        }
        return true;
    }

    private boolean isValidFormat(String contentType,ConstraintValidatorContext constraintValidatorContext) {
        if(Objects.nonNull(allowedTypes) && !allowedTypes.isEmpty()){
            boolean validType = allowedTypes.stream().anyMatch(type -> type.equalsIgnoreCase(contentType));
            if(!validType){
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate("Invalid file type").addConstraintViolation();
                return false;
            }
        }
        return true;
    }

}


