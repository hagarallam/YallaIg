package com.yallaIg.yallaIg_backend.constants;

public final class ErrorConstants {

    // Validation errors
    public static final String ERROR_INVALID_NAME = "Please Enter A Valid Name !";
    public static final String ERROR_INVALID_USER_NAME = "Please Enter A Valid User Name ! Size Must Be Greater Than 7 Character";
    public static final String ERROR_INVALID_EMAIL = "Please Enter A Valid Email !";
    public static final String ERROR_INVALID_PASSWORD = "Your Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit,and one special character.";
    public static final String ERROR_USER_EXISTS = "UserName is used , please use other one !";
    public static final String ERROR_EMAIL_EXISTS = "Email is used , please use other one !";
    public static final String ERROR_CHARGE_AMOUNT = "Charge must be greater then zero";
    public static final String ERROR_INVALID_GRADE = "Grade should be number between 1 and 13";
    public static final String ERROR_NEW_PASSWORD_IS_SAME= "Your Password is same as old password . Please Enter A Valid new Password !";
    public static final String ERROR_REGISTER_COURSE= "You already registered to this course !";
    public static final String ERROR_REGISTER_RESOURCE= "You already registered to this Resource !";
    public static final String ERROR_MULTIPLE_TEACH_APPLICATION= "You already submitted an application with the same email !";


    // Auth error
    public static final String ERROR_USER_NAME_NOT_FOUND = "User is not found ";
    public static final String ERROR_EMAIL_VERIFICATION = "Something wrong in Your Email Verification , Token is expired or incorrect !\n" +
            "please request a new mail verification ! ";
    public static final String ERROR_EMAIL_VERIFICATION_TOKEN_NOT_VALID = "Token is Expired or Incorrect !";
    public static final String ERROR_USER_ALREADY_VERIFIED = "User is already verified , you can login !";

    // Persistence error
    public static final String ERROR_ITEM_NOT_FOUND = "Sorry , Item not found ";
    public static final String ERROR_USER_NOT_FOUND = "Sorry , User not found ";

    // Wallet Error
    public static final String ERROR_CHARGE_WALLET = "Sorry , there is something wrong";


    // Security Error
    public static final String ERROR_TOKEN_NOT_FOUND = "Not Found !";
    public static final String ERROR_TOKEN_EXPIRED = "Expired !";
    public static final String ERROR_PASSWORD_INCORRECT = "Sorry , Your Password is wrong !";

    // invalid requests
    public static final String ERROR_INVALID_REQUEST = "You Request is not valid !";


}