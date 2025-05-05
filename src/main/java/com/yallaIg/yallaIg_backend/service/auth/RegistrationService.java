package com.yallaIg.yallaIg_backend.service.auth;
import com.yallaIg.yallaIg_backend.dto.request.RegisterRequestDto;
import com.yallaIg.yallaIg_backend.model.EmailVerificationToken;
import com.yallaIg.yallaIg_backend.model.User;

public interface RegistrationService {

    User createUser(RegisterRequestDto registerRequestDto);

    boolean isEmailExists(String email);

    Integer createUserWithAllData(RegisterRequestDto registerRequestDto,String appUrl);

    void updateUserAfterVerification(EmailVerificationToken emailVerificationToken);
}
