package com.enigmacamp.latihanspring.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VerificationRequest {
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Verification code is required")
    private String verificationCode;
}
