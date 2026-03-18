package com.example.library.security;

import com.example.library.common.exception.BusinessException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtAuthenticationInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new BusinessException("尚未登入");
        }

        String token = authHeader.substring(7);
        Claims claims;
        try {
            claims = jwtUtil.parseToken(token);
        } catch (Exception e) {
            throw new BusinessException("登入狀態無效，請重新登入");
        }

        Long userId = Long.valueOf(claims.getSubject());
        request.setAttribute("userId", userId);
        return true;
    }
}