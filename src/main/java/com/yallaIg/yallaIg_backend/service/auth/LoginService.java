package com.yallaIg.yallaIg_backend.service.auth;

import com.yallaIg.yallaIg_backend.dto.request.LoginRequestDto;
import org.springframework.security.core.Authentication;

public interface LoginService {

    Authentication authenticateUser(LoginRequestDto loginRequestDto);

    String getToken(Authentication authentication);

    String getRefreshToken(Authentication authentication);

    Long getTokenExpiration();

    Long getRefreshTokenExpiration();

}
