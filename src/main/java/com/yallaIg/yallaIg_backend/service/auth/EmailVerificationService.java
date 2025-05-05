package com.yallaIg.yallaIg_backend.service.auth;

import com.yallaIg.yallaIg_backend.model.EmailVerificationToken;
import com.yallaIg.yallaIg_backend.model.User;

public interface EmailVerificationService {

    void verifyUserEmail(User user,String appUrl);

    EmailVerificationToken findByToken(String token);

    void removeVerification(Integer id);

    void reVerifyUserEmail(User user, String appUrl);
}
