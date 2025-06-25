package com.enigmacamp.latihanspring.controller;

import com.enigmacamp.latihanspring.dto.request.CategoryRequest;
import com.enigmacamp.latihanspring.dto.response.CategoryResponse;
import com.enigmacamp.latihanspring.dto.response.CommonResponse;
import com.enigmacamp.latihanspring.service.CategoryService;
import com.enigmacamp.latihanspring.util.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CommonResponse<CategoryResponse>> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        return ResponseUtil.createResponse(
                HttpStatus.CREATED,
                "Kategori berhasil dibuat",
                categoryService.createCategories(categoryRequest)
        );
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<CategoryResponse>>> getCategories() {
        return ResponseUtil.createResponse(
                HttpStatus.OK,
                "Kategori didapatkan",
                categoryService.getCategories()
        );
    }
}
