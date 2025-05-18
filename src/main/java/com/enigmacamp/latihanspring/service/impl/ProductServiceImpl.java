package com.enigmacamp.latihanspring.service.impl;

import com.enigmacamp.latihanspring.dto.request.ProductRequest;
import com.enigmacamp.latihanspring.dto.response.ProductResponse;
import com.enigmacamp.latihanspring.entity.Product;
import com.enigmacamp.latihanspring.repository.ProductRepository;
import com.enigmacamp.latihanspring.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductResponse> createProduct(List<ProductRequest> product) {

        return product.stream().map(p -> {
            Product newProduct = new Product();
            newProduct.setName(p.getName());
            newProduct.setPrice(p.getPrice());
            newProduct.setDescription(p.getDescription());
            newProduct.setQuantity(p.getQuantity());

            productRepository.save(newProduct);

            return newProduct;
        }).map(this::mapToResponse).toList();
    }

    @Override
    public Optional<ProductResponse> findById(Long id) {
        return productRepository.findById(id).map(this::mapToResponse);
    }

    @Override
    public List<ProductResponse> findByNameContainingIgnoreCase(String name) {
        return productRepository.findAll().stream()
                .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<ProductResponse> findByPriceLessThan(BigDecimal price) {
        return productRepository.findAll().stream()
                .filter(p -> p.getPrice().compareTo(price) < 0)
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<ProductResponse> findByQuantityGreaterThan(Integer quantity) {
        return productRepository.findAll().stream()
                .filter(p -> p.getQuantity().compareTo(quantity) > 0)
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<ProductResponse> findByDescriptionContaining(String description) {
        return productRepository.findAll().stream()
                .filter(p -> p.getDescription().toLowerCase().contains(description.toLowerCase()))
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest product) {
        Product updateProduct = productRepository.findById(id).orElse(null);
        updateProduct.setName(product.getName());
        updateProduct.setPrice(product.getPrice());
        updateProduct.setDescription(product.getDescription());
        updateProduct.setQuantity(product.getQuantity());
        productRepository.save(updateProduct);
        return mapToResponse(updateProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private ProductResponse mapToResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setPrice(product.getPrice());
        productResponse.setDescription(product.getDescription());
        productResponse.setQuantity(product.getQuantity());
        productResponse.setCreatedAt(product.getCreatedAt());
        productResponse.setUpdatedAt(product.getUpdatedAt());
        return productResponse;
    }
}
