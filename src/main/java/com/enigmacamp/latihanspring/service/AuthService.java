package com.enigmacamp.latihanspring.service;

import com.enigmacamp.latihanspring.dto.request.AuthRequest;
import com.enigmacamp.latihanspring.dto.response.LoginResponse;
import com.enigmacamp.latihanspring.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(AuthRequest request);
    LoginResponse login(AuthRequest request);
}
