package com.yallaIg.yallaIg_backend.controller;

import com.yallaIg.yallaIg_backend.dto.response.GenericApiResponse;
import com.yallaIg.yallaIg_backend.dto.response.GenericApiResponseError;
import com.yallaIg.yallaIg_backend.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(value = BAD_REQUEST)
    public @ResponseBody ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().stream().forEach(error -> {
            String fieldName = error instanceof FieldError ? ((FieldError) error).getField() : error.getObjectName();
            errors.put(fieldName,error.getDefaultMessage());
        });
        return new ResponseEntity<>(errors,BAD_REQUEST);
    }

    @ExceptionHandler(value = BusinessValidationException.class)
    @ResponseStatus(value = CONFLICT)
    public @ResponseBody GenericApiResponseError handleBusinessValidationException(BusinessValidationException ex){
        return new GenericApiResponseError(ex.getMessage(), CONFLICT.value());
    }

    @ExceptionHandler(value = GeneralValidationException.class)
    @ResponseStatus(value = BAD_REQUEST)
    public @ResponseBody GenericApiResponseError handleGeneralValidationException(GeneralValidationException ex){
        return new GenericApiResponseError(ex.getMessage(), BAD_REQUEST.value());
    }

    @ExceptionHandler(value = ItemNotFoundException.class)
    @ResponseStatus(value = NOT_FOUND)
    public @ResponseBody GenericApiResponseError handleItemNotFoundException(ItemNotFoundException ex){
        return new GenericApiResponseError(ex.getMessage(), NOT_FOUND.value());
    }

    @ExceptionHandler(value = TokenExpiredException.class)
    @ResponseStatus(value = BAD_REQUEST)
    public @ResponseBody GenericApiResponseError handleTokenExpiredException(TokenExpiredException ex){
        return new GenericApiResponseError(ex.getMessage(), BAD_REQUEST.value());
    }

    @ExceptionHandler(value = DisabledException.class)
    @ResponseStatus(value = UNAUTHORIZED)
    public @ResponseBody GenericApiResponseError handleAuthenticationException(DisabledException ex){
        return new GenericApiResponseError(ex.getMessage(), UNAUTHORIZED.value());
    }
//
//    @ExceptionHandler(value = Exception.class)
//    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
//    public @ResponseBody GenericApiResponseError handleGeneralException(Exception ex){
//        return new GenericApiResponseError(INTERNAL_SERVER_ERROR.toString(), INTERNAL_SERVER_ERROR.value());
//    }
}
