package com.yallaIg.yallaIg_backend.exception;

public class TokenExpiredException extends RuntimeException{


    public TokenExpiredException(String message){
        super(message);
    }

}
