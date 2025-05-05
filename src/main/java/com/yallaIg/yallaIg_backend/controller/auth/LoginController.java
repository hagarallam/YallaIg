package com.yallaIg.yallaIg_backend.controller.auth;

import com.yallaIg.yallaIg_backend.dto.request.ResetPasswordRequest;
import com.yallaIg.yallaIg_backend.dto.response.GenericApiResponse;
import com.yallaIg.yallaIg_backend.dto.request.LoginRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.AuthenticationResponseDto;
import com.yallaIg.yallaIg_backend.service.auth.AuthenticationService;
import com.yallaIg.yallaIg_backend.util.ApiRequestUtils;
import com.yallaIg.yallaIg_backend.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.yallaIg.yallaIg_backend.constants.SuccessConstants.SUCCESS_LOGIN;
import static com.yallaIg.yallaIg_backend.constants.SuccessConstants.SUCCESS_RESET_PASSWORD_MESSAGE;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth Controller",description = "Public Auth endpoints")
public class LoginController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @SecurityRequirements()
    @Operation(summary = "User login", description = "Authenticate user and return JWT token")
    public ResponseEntity<GenericApiResponse<AuthenticationResponseDto>> login(@RequestBody @Valid LoginRequestDto loginRequestDto){
        AuthenticationResponseDto authenticationResponseDto = authenticationService.loginUser(loginRequestDto);
        GenericApiResponse<AuthenticationResponseDto> genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setPayload(authenticationResponseDto);
        genericApiResponse.setStatusCode(OK.value());
        genericApiResponse.setMessage(SUCCESS_LOGIN);
        return new ResponseEntity<>(genericApiResponse,OK);
    }


    @PostMapping("/forgot-password")
    @SecurityRequirements()
    @Operation(summary = "Forget Password", description = "Forget password for user")
    public ResponseEntity<GenericApiResponse> forgetPassword(@RequestParam String userEmail, HttpServletRequest request){
        authenticationService.initiatePasswordReset(userEmail, ApiRequestUtils.getAppUrl(request));
        GenericApiResponse genericApiResponse = new GenericApiResponse();
        genericApiResponse.setMessage(SUCCESS_RESET_PASSWORD_MESSAGE);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse,OK);
    }



    @PostMapping("/reset-password")
    @SecurityRequirements()
    @Operation(summary = "Reset Password", description = "Reset password for user")
    public ResponseEntity<GenericApiResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest){
        String result = authenticationService.resetPassword(resetPasswordRequest.getToken(),resetPasswordRequest.getNewPassword());
        GenericApiResponse genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setMessage(result);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse,OK);
    }


    @PostMapping("/refresh-token")
    public ResponseEntity<Void> refreshToken(HttpServletRequest request , HttpServletResponse response) throws IOException {
        authenticationService.createAccessTokenByRefreshToken(request,response);
        return new ResponseEntity<>(OK);
    }

}
