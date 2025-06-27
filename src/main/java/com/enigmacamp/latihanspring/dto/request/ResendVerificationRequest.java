package com.enigmacamp.latihanspring.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResendVerificationRequest {
    @NotBlank(message = "Email is required")
    private String email;
}
