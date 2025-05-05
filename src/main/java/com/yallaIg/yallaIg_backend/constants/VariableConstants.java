package com.yallaIg.yallaIg_backend.constants;

import org.springframework.http.MediaType;

import java.util.List;

public final class VariableConstants {

    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    public static final String PASSWORD_REGEX = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";

    // number const
    public static final int JWT_SECRET_KEY_BYTE_SIZE = 32;
    public static final int JWT_SECRET_KEY_SIZE = 64;
    public static final Long JWT_EXPIRATION = 1_800_000L;     // 30 min in millisecond
    public static final Long JWT_REFRESH_EXPIRATION = 604_800_000L;     // 7 days in millisecond
    public static final int PASSWORD_RESET_TOKEN_EXPIRATION = 1 ; // 1 hour



}
