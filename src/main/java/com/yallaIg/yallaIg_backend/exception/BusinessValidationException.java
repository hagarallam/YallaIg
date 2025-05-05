package com.yallaIg.yallaIg_backend.exception;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class BusinessValidationException extends RuntimeException{


    public BusinessValidationException(String message){
        super(message);
    }

}
