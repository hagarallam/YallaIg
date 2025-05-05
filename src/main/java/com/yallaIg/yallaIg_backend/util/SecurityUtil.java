package com.yallaIg.yallaIg_backend.util;

import com.yallaIg.yallaIg_backend.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public final class SecurityUtil {

    private SecurityUtil(){
    }

    public static Integer getCurrentUserId(){
        return ((User)SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal()).getUserId();
    }

    public static String extractTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(Objects.nonNull(authHeader) && authHeader.startsWith("Bearer ")){
            return authHeader.substring(7);
        }
        return null;
    }
}
