package com.yallaIg.yallaIg_backend.security;

import com.yallaIg.yallaIg_backend.model.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.yallaIg.yallaIg_backend.constants.VariableConstants.JWT_EXPIRATION;
import static com.yallaIg.yallaIg_backend.constants.VariableConstants.JWT_REFRESH_EXPIRATION;

@Service
public class JwtService {

    public String generateToken( UserDetails userDetails){
        Map<String,Object> claims = new HashMap<>();
        claims.put("roles",userDetails.getAuthorities());
        return JwtUtil.buildToken(userDetails.getUsername(),claims, JWT_EXPIRATION);
    }
    public String generateRefreshToken(Authentication authentication){
        String email = ((User)authentication.getPrincipal()).getUsername();
        Map<String,Object> claims = new HashMap<>();
        claims.put("roles",authentication.getAuthorities());
        return JwtUtil.buildToken(email,claims, JWT_REFRESH_EXPIRATION);
    }

    public String extractEmail(String token){
        return JwtUtil.extractClaim(token,Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return JwtUtil.extractClaim(token,Claims::getExpiration);
    }

    public boolean isTokenValid(String token,String email){
        String tokenEmail = extractEmail(token);
        return email.equals(tokenEmail) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token){
        Date expiration = extractExpiration(token);
        return expiration.before(new Date());
    }

    public long getExpirationTime() {
        return JWT_EXPIRATION;
    }
    public long getRefreshExpirationTime() {
        return JWT_REFRESH_EXPIRATION;
    }
}
