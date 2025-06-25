package com.enigmacamp.latihanspring.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequest {
    @NotBlank(message = "Category tidak boleh kosong")
    private String name;
}
