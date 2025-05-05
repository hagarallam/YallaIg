package com.yallaIg.yallaIg_backend.service.auth;

import com.yallaIg.yallaIg_backend.model.PasswordResetToken;
import com.yallaIg.yallaIg_backend.model.User;

public interface PasswordResetTokenService {

    void createPasswordResetToken(User user , String token);

    PasswordResetToken findByToken(String token);

    void deleteToken(PasswordResetToken passwordResetToken);
}
