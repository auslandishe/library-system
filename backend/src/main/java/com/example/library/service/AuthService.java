package com.example.library.service;

import com.example.library.common.exception.BusinessException;
import com.example.library.common.dto.request.LoginRequest;
import com.example.library.common.dto.request.RegisterRequest;
import com.example.library.common.dto.response.LoginResponse;
import com.example.library.common.model.User;
import com.example.library.repository.UserRepository;
import com.example.library.security.JwtUtil;
import com.example.library.security.PasswordUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordUtil passwordUtil;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordUtil passwordUtil, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordUtil = passwordUtil;
        this.jwtUtil = jwtUtil;
    }

    public void register(RegisterRequest request) {
        if (userRepository.existsByPhoneNumber(request.phoneNumber())) {
            throw new BusinessException("手機號碼已註冊");
        }

        String encodedPassword = passwordUtil.hash(request.password());
        userRepository.insert(request.phoneNumber(), encodedPassword, request.userName());
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByPhoneNumber(request.phoneNumber());
        if (user == null) {
            throw new BusinessException("帳號或密碼錯誤");
        }

        if (!passwordUtil.matches(request.password(), user.getPassword())) {
            throw new BusinessException("帳號或密碼錯誤");
        }

        userRepository.updateLastLoginTime(user.getUserId());
        String token = jwtUtil.generateToken(user.getUserId(), user.getPhoneNumber());
        return new LoginResponse(token, user.getUserId(), user.getUserName());
    }
}
