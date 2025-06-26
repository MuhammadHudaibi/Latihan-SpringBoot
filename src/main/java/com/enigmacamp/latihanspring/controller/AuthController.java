package com.enigmacamp.latihanspring.controller;

import com.enigmacamp.latihanspring.dto.request.AuthRequest;
import com.enigmacamp.latihanspring.dto.response.CommonResponse;
import com.enigmacamp.latihanspring.dto.response.LoginResponse;
import com.enigmacamp.latihanspring.dto.response.RegisterResponse;
import com.enigmacamp.latihanspring.service.AuthService;
import com.enigmacamp.latihanspring.util.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<CommonResponse<RegisterResponse>> register(@Valid @RequestBody AuthRequest request) {
        return ResponseUtil.createResponse(
                HttpStatus.CREATED,
                "User registered successfully",
                authService.register(request)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<LoginResponse>> login(@Valid @RequestBody AuthRequest request) {
        return ResponseUtil.createResponse(
                HttpStatus.OK,
                "Login successful",
                authService.login(request)
        );
    }
}
