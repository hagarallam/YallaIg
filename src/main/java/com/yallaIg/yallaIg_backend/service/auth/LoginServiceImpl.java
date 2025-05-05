package com.yallaIg.yallaIg_backend.service.auth;

import com.yallaIg.yallaIg_backend.dto.request.LoginRequestDto;
import com.yallaIg.yallaIg_backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public Authentication authenticateUser(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequestDto.getEmail(),
                loginRequestDto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    @Override
    public String getToken(Authentication authentication) {
        return jwtService.generateToken((UserDetails) authentication.getPrincipal());
    }

    @Override
    public String getRefreshToken(Authentication authentication) {
        return jwtService.generateRefreshToken(authentication);
    }

    @Override
    public Long getTokenExpiration() {
        return jwtService.getExpirationTime();
    }

    @Override
    public Long getRefreshTokenExpiration() {
        return jwtService.getRefreshExpirationTime();
    }
}
