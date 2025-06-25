package com.enigmacamp.latihanspring.dto.request;

import com.enigmacamp.latihanspring.model.ProductStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private ProductStatus status;

    @NotNull(message = "ID kategori dibutuhkan")
    private Long categoryId;
}
