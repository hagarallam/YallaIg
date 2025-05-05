package com.yallaIg.yallaIg_backend.exception;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class GeneralValidationException extends RuntimeException{


    public GeneralValidationException(String message){
        super(message);
    }

}
