package com.yallaIg.yallaIg_backend.service.auth;

import com.yallaIg.yallaIg_backend.dto.request.LoginRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.AuthenticationResponseDto;
import com.yallaIg.yallaIg_backend.dto.request.RegisterRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {


    Integer registerUser(RegisterRequestDto registerRequestDto,String appUrl);

    AuthenticationResponseDto loginUser(LoginRequestDto loginRequestDto);

    String resetPassword(String token,String newPassword);

    void initiatePasswordReset(String userEmail,String appUrl);

    void createAccessTokenByRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void verifyUserEmail(String token);

    void resendVerificationEmail(String email,String appUrl);
}
