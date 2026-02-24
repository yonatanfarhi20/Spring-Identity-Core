package com.devops.SpringIdentityCore.service;

import com.devops.SpringIdentityCore.dtos.requests.LoginRequest;
import com.devops.SpringIdentityCore.dtos.requests.UserRegistrationRequest;
import com.devops.SpringIdentityCore.dtos.responses.AuthResponse;
import com.devops.SpringIdentityCore.dtos.responses.UserResponse;
import com.devops.SpringIdentityCore.mapper.UserMapper;
import com.devops.SpringIdentityCore.model.User;
import com.devops.SpringIdentityCore.repository.UserRepository;
import com.devops.SpringIdentityCore.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public UserResponse registerUser(UserRegistrationRequest request) {
        log.info("ניסיון רישום משתמש חדש: {}", request.email());
        if (userRepository.existsByEmail(request.email().toLowerCase().trim())) {
            throw new RuntimeException("כתובת האימייל כבר קיימת במערכת");
        }
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email().toLowerCase().trim())
                .orElseThrow(() -> new RuntimeException("משתמש לא נמצא או סיסמה שגויה"));
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("משתמש לא נמצא או סיסמה שגויה");
        }
        String token = jwtService.generateToken(user.getEmail(), user.getRole().toString());
        return new AuthResponse(token, user.getEmail(), user.getFullName());
    }
}