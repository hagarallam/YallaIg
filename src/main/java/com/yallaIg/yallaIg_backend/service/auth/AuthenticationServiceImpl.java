package com.yallaIg.yallaIg_backend.service.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yallaIg.yallaIg_backend.constants.ErrorConstants;
import com.yallaIg.yallaIg_backend.dto.request.LoginRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.AuthenticationResponseDto;
import com.yallaIg.yallaIg_backend.dto.request.RegisterRequestDto;
import com.yallaIg.yallaIg_backend.enums.RoleEnum;
import com.yallaIg.yallaIg_backend.exception.BusinessValidationException;
import com.yallaIg.yallaIg_backend.exception.ItemNotFoundException;
import com.yallaIg.yallaIg_backend.exception.TokenExpiredException;
import com.yallaIg.yallaIg_backend.exception.GeneralValidationException;
import com.yallaIg.yallaIg_backend.model.EmailVerificationToken;
import com.yallaIg.yallaIg_backend.model.PasswordResetToken;
import com.yallaIg.yallaIg_backend.model.User;
import com.yallaIg.yallaIg_backend.security.CustomUserDetailsService;
import com.yallaIg.yallaIg_backend.security.JwtService;
import com.yallaIg.yallaIg_backend.service.core.crud.UserService;
import com.yallaIg.yallaIg_backend.util.ResetPasswordUtil;
import com.yallaIg.yallaIg_backend.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.yallaIg.yallaIg_backend.constants.ErrorConstants.*;
import static com.yallaIg.yallaIg_backend.constants.SuccessConstants.SUCCESS_RESET_PASSWORD;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

    private final RegistrationService registrationService;
    private final LoginService loginService;
    private final UserService userService;
    private final PasswordResetTokenService passwordResetTokenService;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;
    private final EmailVerificationService emailVerificationService;

    @Override
    public Integer registerUser(RegisterRequestDto registerRequestDto,String appUrl) {

        if(registrationService.isEmailExists(registerRequestDto.getEmail())){
            throw new GeneralValidationException(ErrorConstants.ERROR_EMAIL_EXISTS);
        }

        return registrationService.createUserWithAllData(registerRequestDto,appUrl);
    }


    @Override
    public AuthenticationResponseDto loginUser(LoginRequestDto loginRequestDto) {
        Authentication authentication = loginService.authenticateUser(loginRequestDto);
        return createLoginResponse(authentication);
    }

    private AuthenticationResponseDto createLoginResponse(Authentication authentication) {
        String token = loginService.getToken(authentication);
        String refreshToken = loginService.getRefreshToken(authentication);
        Long accessTokenExpiration = loginService.getTokenExpiration();
        Long refreshTokenExpiration = loginService.getRefreshTokenExpiration();
        return new AuthenticationResponseDto(token,refreshToken, RoleEnum.valueOf(authentication.getAuthorities().stream().findFirst().get().getAuthority()),accessTokenExpiration,refreshTokenExpiration);
    }

    @Override
    public void initiatePasswordReset(String email,String appUrl) {
        User user = userService.findByEmail(email);
        if(Objects.isNull(user))
            throw new ItemNotFoundException(ERROR_USER_NOT_FOUND);

        userService.createPasswordResetTokenForUser(user,appUrl);

    }

    @Override
    public String resetPassword(String token,String newPassword) {
        PasswordResetToken passwordResetToken = passwordResetTokenService.findByToken(token);

        if(!ResetPasswordUtil.isTokenFound(passwordResetToken))
            throw new ItemNotFoundException(ERROR_TOKEN_NOT_FOUND);

        if(ResetPasswordUtil.isTokenExpired(passwordResetToken))
            throw new TokenExpiredException(ERROR_TOKEN_EXPIRED);

        userService.updateUserPassword(passwordResetToken.getUser(),newPassword);

        passwordResetTokenService.deleteToken(passwordResetToken);

        return SUCCESS_RESET_PASSWORD;

    }

    @Override
    public void createAccessTokenByRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String refreshToken = SecurityUtil.extractTokenFromRequest(request);

        // no refresh refreshToken
        if(Objects.isNull(refreshToken)) return;

        String extractedEmail = jwtService.extractEmail(refreshToken);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(extractedEmail);
        if(jwtService.isTokenValid(refreshToken,userDetails.getUsername())){
            createAccessToken(response, userDetails, refreshToken);
        }

    }

    @Override
    public void verifyUserEmail(String token) {
        EmailVerificationToken emailVerificationToken = emailVerificationService.findByToken(token);
        if(Objects.isNull(emailVerificationToken) || emailVerificationToken.getExpiryDate().isBefore(LocalDateTime.now())){
            throw new GeneralValidationException(ERROR_EMAIL_VERIFICATION);
        }
        registrationService.updateUserAfterVerification(emailVerificationToken);

    }

    @Override
    public void resendVerificationEmail(String email,String appUrl) {
        User user = userService.findByEmail(email);
        if(Objects.isNull(user)){
            throw new ItemNotFoundException(ERROR_USER_NOT_FOUND);
        }
        if(user.isMailVerified()){
            throw new BusinessValidationException(ERROR_USER_ALREADY_VERIFIED);
        }
        emailVerificationService.reVerifyUserEmail(user,appUrl);
    }

    private void createAccessToken(HttpServletResponse response, UserDetails userDetails, String refreshToken) throws IOException {
        String accessToken = jwtService.generateToken(userDetails);
        AuthenticationResponseDto authenticationResponseDto = new AuthenticationResponseDto(
                accessToken,
                refreshToken,
                RoleEnum.valueOf(userDetails.getAuthorities().stream().findFirst().get().getAuthority()),
                jwtService.getExpirationTime(),
                jwtService.getRefreshExpirationTime());
        new ObjectMapper().writeValue(response.getOutputStream(),authenticationResponseDto);
    }


}
