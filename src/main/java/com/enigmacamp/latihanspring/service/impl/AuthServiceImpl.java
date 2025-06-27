package com.enigmacamp.latihanspring.service.impl;

import com.enigmacamp.latihanspring.dto.request.AuthRequest;
import com.enigmacamp.latihanspring.dto.response.LoginResponse;
import com.enigmacamp.latihanspring.dto.response.RegisterResponse;
import com.enigmacamp.latihanspring.entity.User;
import com.enigmacamp.latihanspring.repository.UserRepository;
import com.enigmacamp.latihanspring.security.JwtService;
import com.enigmacamp.latihanspring.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

import com.enigmacamp.latihanspring.dto.request.ResendVerificationRequest;
import com.enigmacamp.latihanspring.dto.request.VerificationRequest;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailServiceImpl emailService;

    @Override
    public RegisterResponse register(AuthRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ROLE_USER");

        String code = generateRandomCode(6);
        user.setVerificationToken(code);
        user.setCodeExpiration(LocalDateTime.now().plusMinutes(3));
        user.setVerified(false);

        userRepository.save(user);

        emailService.sendVerificationCodeEmail(user.getEmail(), code);

        return RegisterResponse.builder()
                .username(user.getUsername())
                .build();
    }

    @Override
    public LoginResponse login(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = (User) authentication.getPrincipal();

        if (!user.isVerified()) {
            throw new RuntimeException("Account not verified");
        }

        String token = jwtService.generateToken(user);

        return LoginResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public void verify(VerificationRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.isVerified()) {
            throw new RuntimeException("Account already verified");
        }

        if (user.getVerificationToken().equals(request.getVerificationCode())) {
            if (user.getCodeExpiration().isAfter(LocalDateTime.now())) {
                user.setVerified(true);
                user.setVerificationToken(null);
                user.setCodeExpiration(null);
                userRepository.save(user);
            } else {
                throw new RuntimeException("Verification code has expired");
            }
        } else {
            throw new RuntimeException("Invalid verification code");
        }
    }

    @Override
    public void resendVerification(ResendVerificationRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.isVerified()) {
            throw new RuntimeException("Account already verified");
        }

        String code = generateRandomCode(6);
        user.setVerificationToken(code);
        user.setCodeExpiration(LocalDateTime.now().plusMinutes(3));
        userRepository.save(user);

        emailService.sendVerificationCodeEmail(user.getEmail(), code);
    }

    private String generateRandomCode(int length) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
}
