package com.yallaIg.yallaIg_backend.controller.auth;

import com.yallaIg.yallaIg_backend.dto.response.GenericApiResponse;
import com.yallaIg.yallaIg_backend.dto.request.RegisterRequestDto;
import com.yallaIg.yallaIg_backend.service.auth.AuthenticationService;
import com.yallaIg.yallaIg_backend.util.ApiRequestUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

import static com.yallaIg.yallaIg_backend.constants.ErrorConstants.ERROR_EMAIL_VERIFICATION;
import static com.yallaIg.yallaIg_backend.constants.SuccessConstants.*;
import static org.springframework.http.HttpStatus.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Register" ,description = "Public Register endpoints")
public class RegisterController {

    @Value("${front-port}")
    private String frontPort;

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @SecurityRequirements()
    @Operation(summary = "User registration", description = "Register a new user")
    public ResponseEntity<GenericApiResponse<Integer>> register(@ModelAttribute @Valid RegisterRequestDto registerRequestDto, HttpServletRequest request){
        Integer id = authenticationService.registerUser(registerRequestDto, ApiRequestUtils.getFullAppUrl(request));
        GenericApiResponse<Integer> genericApiResponse = new GenericApiResponse<>(id, SUCCESS_REGISTERED_USER,CREATED.value());
        return new ResponseEntity<>(genericApiResponse,CREATED);
    }

    @GetMapping("/verify-email")
    public void verifyEmail(@RequestParam String token, HttpServletResponse response, HttpServletRequest request) throws IOException {
        authenticationService.verifyUserEmail(token);
        String url = ApiRequestUtils.getAppUrl(request)+ frontPort +"/auth/verify";
        response.sendRedirect(url);
    }
    @PostMapping("/resend-verification")
    public ResponseEntity<GenericApiResponse<String>> resendVerification(@RequestParam String email,HttpServletRequest request) {
        authenticationService.resendVerificationEmail(email,ApiRequestUtils.getFullAppUrl(request));
        GenericApiResponse<String> genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setMessage(SUCCESS_REVERIFICATION);
        genericApiResponse.setStatusCode(OK.value());
        return ResponseEntity.ok(genericApiResponse);
    }

}
