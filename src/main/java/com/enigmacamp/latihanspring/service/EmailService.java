package com.enigmacamp.latihanspring.service;

public interface EmailService {
    void sendVerificationCodeEmail(String toEmail, String verificationCode);
}
