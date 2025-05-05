package com.yallaIg.yallaIg_backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import static com.yallaIg.yallaIg_backend.constants.VariableConstants.*;

public final class JwtUtil {

    private static final String secret = generateSecretKey();

    private JwtUtil(){
        
    }

    public static String buildToken(String email, Map<String, Object> claims, Long jwtExpiration) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date( System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(),SignatureAlgorithm.HS256)
                .compact();
    }

    private static Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private static Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private static String generateSecretKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] keyBytes = new byte[JWT_SECRET_KEY_BYTE_SIZE];
        secureRandom.nextBytes(keyBytes);

        StringBuilder hexString = new StringBuilder(JWT_SECRET_KEY_SIZE);
        for (byte b : keyBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
