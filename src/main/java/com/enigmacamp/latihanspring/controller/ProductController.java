package com.enigmacamp.latihanspring.controller;

import com.enigmacamp.latihanspring.dto.request.ProductRequest;
import com.enigmacamp.latihanspring.dto.response.CommonResponse;
import com.enigmacamp.latihanspring.dto.response.ProductResponse;
import com.enigmacamp.latihanspring.service.ProductService;
import com.enigmacamp.latihanspring.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<CommonResponse<List<ProductResponse>>> save(@RequestBody List<ProductRequest> product) {
        return ResponseUtil.createResponse(
                HttpStatus.CREATED,
                "Produk Berhasil dibuat",
                productService.createProduct(product)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<Optional<ProductResponse>>> findById(@PathVariable Long id) {
        return ResponseUtil.createResponse(
                HttpStatus.OK,
                "Produk ditemukan",
                productService.findById(id)
        );
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<ProductResponse>>> findAll() {
        return ResponseUtil.createResponse(
                HttpStatus.OK,
                "Daftar produk ditemukan",
                productService.findAll()
        );
    }

    @GetMapping("/search/by-name")
    public ResponseEntity<CommonResponse<List<ProductResponse>>> findByNameContainingIgnoreCase(@RequestParam String name) {
        return ResponseUtil.createResponse(
                HttpStatus.OK,
                "Daftar produk berhasil didapatkan",
                productService.findByNameContainingIgnoreCase(name)
        );
    }

    @GetMapping("/search/by-price-less-than")
    public ResponseEntity<CommonResponse<List<ProductResponse>>> findByPriceLessThan(@RequestParam BigDecimal price) {
        return ResponseUtil.createResponse(
                HttpStatus.OK,
                "Daftar produk didapatkan",
                productService.findByPriceLessThan(price)
        );
    }

    @GetMapping("/by-quantity-greater-than")
    public ResponseEntity<CommonResponse<List<ProductResponse>>> findByQuantityGreaterThan(@RequestParam Integer quantity) {
        return ResponseUtil.createResponse(
                HttpStatus.OK,
                "Daftar produk didapatkan",
                productService.findByQuantityGreaterThan(quantity)
        );
    }

    @GetMapping("/search/created-between")
    public ResponseEntity<CommonResponse<List<ProductResponse>>> findProductCreatedAtBetween(@RequestParam LocalDateTime start, @RequestParam LocalDateTime end) {
        return ResponseUtil.createResponse(
                HttpStatus.OK,
                "Daftar produk didapatkan",
                productService.findProductCreatedAtBetween(start, end)
        );
    }

    @GetMapping("/count-product-with-price-greater-than")
    public ResponseEntity<CommonResponse<Long>> countProductWithPriceGreaterThan(@RequestParam BigDecimal price) {
        return ResponseUtil.createResponse(
                HttpStatus.OK,
                "Jumlah produk didapatkan",
                productService.countProductsWithPriceGreaterThan(price)
        );
    }

    @GetMapping("/by-description")
    public ResponseEntity<CommonResponse<List<ProductResponse>>> findByDescriptionContaining(@RequestParam String description) {
        return ResponseUtil.createResponse(
                HttpStatus.OK,
                "Daftar produk didapatkan",
                productService.findByDescriptionContaining(description)
        );
    }

    @PutMapping
    public ResponseEntity<CommonResponse<ProductResponse>> update(@RequestParam Long id, @RequestBody ProductRequest product) {
        return ResponseUtil.createResponse(
                HttpStatus.OK,
                "Produk berhasil diperbaharui",
                productService.updateProduct(id, product)
        );
    }

    @DeleteMapping
    public ResponseEntity<CommonResponse<Objects>> deleteById(@RequestParam Long id) {
        productService.deleteProduct(id);
        return ResponseUtil.createResponse(
                HttpStatus.OK,
                "Produk berhasil dihapus dengan id: " + id,
                null
        );
    }
}
