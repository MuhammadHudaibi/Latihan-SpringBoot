package com.enigmacamp.latihanspring.service;

import com.enigmacamp.latihanspring.dto.request.ProductRequest;
import com.enigmacamp.latihanspring.dto.response.ProductResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductResponse> createProduct(List<ProductRequest> product);
    Optional<ProductResponse> findById(Long id);
    List<ProductResponse> findByNameContainingIgnoreCase(String name);
    List<ProductResponse> findByPriceLessThan(BigDecimal price);
    List<ProductResponse> findByQuantityGreaterThan(Integer quantity);
    List<ProductResponse> findByDescriptionContaining(String description);
    List<ProductResponse> findAll();
    ProductResponse updateProduct(Long id, ProductRequest product);
    void deleteProduct(Long id);
    List<ProductResponse> findProductCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    Long countProductsWithPriceGreaterThan(BigDecimal price);
}
