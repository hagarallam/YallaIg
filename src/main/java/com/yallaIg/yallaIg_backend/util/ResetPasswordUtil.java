package com.yallaIg.yallaIg_backend.util;

import com.yallaIg.yallaIg_backend.model.PasswordResetToken;

import java.time.LocalDateTime;
import java.util.Objects;

public final class ResetPasswordUtil {

    private ResetPasswordUtil(){

    }

    public static boolean isTokenFound(PasswordResetToken passToken) {
        return Objects.nonNull(passToken);
    }

    public static boolean isTokenExpired(PasswordResetToken passToken) {
        return passToken.getExpiryDate().isBefore(LocalDateTime.now());
    }}
