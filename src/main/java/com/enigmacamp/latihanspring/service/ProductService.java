package com.enigmacamp.latihanspring.service;

import com.enigmacamp.latihanspring.dto.request.ProductRequest;
import com.enigmacamp.latihanspring.dto.response.ProductResponse;
import com.enigmacamp.latihanspring.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    ProductResponse createProduct(ProductRequest product);
    Optional<ProductResponse> findById(Long id);
    List<ProductResponse> findAll();
    ProductResponse updateProduct(Long id, ProductRequest product);
    void deleteProduct(Long id);
}
