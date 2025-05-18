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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<CommonResponse<ProductResponse>> save(@RequestBody ProductRequest product) {
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
