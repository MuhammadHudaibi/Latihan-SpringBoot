package com.enigmacamp.latihanspring.service.impl;

import com.enigmacamp.latihanspring.dto.request.ProductRequest;
import com.enigmacamp.latihanspring.dto.response.CategoryResponse;
import com.enigmacamp.latihanspring.dto.response.ProductResponse;
import com.enigmacamp.latihanspring.entity.Category;
import com.enigmacamp.latihanspring.entity.Product;
import com.enigmacamp.latihanspring.repository.ProductRepository;
import com.enigmacamp.latihanspring.service.CategoryService;
import com.enigmacamp.latihanspring.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    @Override
    public List<ProductResponse> createProduct(List<ProductRequest> product) {

        List<Product> productsToSave = product.stream().map(p -> {
            Product newProduct = new Product();
            Category category = categoryService.getCategoryById(p.getCategoryId());

            newProduct.setName(p.getName());
            newProduct.setPrice(p.getPrice());
            newProduct.setDescription(p.getDescription());
            newProduct.setQuantity(p.getQuantity());
            newProduct.setStatus(p.getStatus());
            newProduct.setCategory(category);
            return newProduct;
        }).toList();

        productRepository.saveAll(productsToSave);

        return productsToSave.stream().map(this::mapToResponse).toList();
    }

    @Override
    public Optional<ProductResponse> findById(Long id) {
        return productRepository.findById(id).map(this::mapToResponse);
    }

    @Override
    public List<ProductResponse> findByNameContainingIgnoreCase(String name) {
        return productRepository.findByNameContainingIgnoreCase(name)
                .stream().map(this::mapToResponse).toList();
    }

    @Override
    public List<ProductResponse> findByPriceLessThan(BigDecimal price) {
        return productRepository.findByPriceLessThan(price)
                .stream().map(this::mapToResponse).toList();
    }

    @Override
    public List<ProductResponse> findByQuantityGreaterThan(Integer quantity) {
        return productRepository.findByQuantityGreaterThan(quantity)
                .stream().map(this::mapToResponse).toList();
    }

    @Override
    public List<ProductResponse> findByDescriptionContaining(String description) {
        return productRepository.findByDescriptionContainingIgnoreCase(description)
                .stream().map(this::mapToResponse).toList();
    }

    @Override
    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProductResponse updateProduct(Long id, ProductRequest product) {
        Product updateProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produk tidak ditemukan dengan id: " + id));

        Category category = categoryService.getCategoryById(product.getCategoryId());

        updateProduct.setName(product.getName());
        updateProduct.setPrice(product.getPrice());
        updateProduct.setDescription(product.getDescription());
        updateProduct.setQuantity(product.getQuantity());
        updateProduct.setStatus(product.getStatus());
        updateProduct.setCategory(category);
        productRepository.save(updateProduct);
        return mapToResponse(updateProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductResponse> findProductCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return productRepository.findProductCreatedAtBetween(startDate, endDate)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public Long countProductsWithPriceGreaterThan(BigDecimal price) {
        return productRepository.countProductsWithPriceGreaterThan(price);
    }

    private ProductResponse mapToResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setPrice(product.getPrice());
        productResponse.setDescription(product.getDescription());
        productResponse.setQuantity(product.getQuantity());
        productResponse.setStatus(product.getStatus());

        if (product.getCategory() != null) {
            productResponse.setCategory(
                    CategoryResponse.builder()
                            .id(product.getCategory().getId())
                            .name(product.getCategory().getName())
                            .build()
            );
        }

        productResponse.setCreatedAt(product.getCreatedAt());
        productResponse.setUpdatedAt(product.getUpdatedAt());
        return productResponse;
    }
}
