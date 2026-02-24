package com.devops.SpringIdentityCore.controllers;

import com.devops.SpringIdentityCore.dtos.requests.LoginRequest;
import com.devops.SpringIdentityCore.dtos.requests.UserRegistrationRequest;
import com.devops.SpringIdentityCore.dtos.responses.AuthResponse;
import com.devops.SpringIdentityCore.dtos.responses.UserResponse;
import com.devops.SpringIdentityCore.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "ממשק התחברות ורישום")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "רישום משתמש חדש")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRegistrationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerUser(request));
    }

    @PostMapping("/login")
    @Operation(summary = "התחברות למערכת")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}