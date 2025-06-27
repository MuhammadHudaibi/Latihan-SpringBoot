package com.enigmacamp.latihanspring.service;

import com.enigmacamp.latihanspring.dto.request.AuthRequest;
import com.enigmacamp.latihanspring.dto.response.LoginResponse;
import com.enigmacamp.latihanspring.dto.response.RegisterResponse;

import com.enigmacamp.latihanspring.dto.request.ResendVerificationRequest;
import com.enigmacamp.latihanspring.dto.request.VerificationRequest;

public interface AuthService {
    RegisterResponse register(AuthRequest request);
    LoginResponse login(AuthRequest request);
    void verify(VerificationRequest request);
    void resendVerification(ResendVerificationRequest request);
}
