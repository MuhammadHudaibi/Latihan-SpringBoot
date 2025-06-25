package com.enigmacamp.latihanspring.service;

import com.enigmacamp.latihanspring.dto.request.CategoryRequest;
import com.enigmacamp.latihanspring.dto.response.CategoryResponse;
import com.enigmacamp.latihanspring.entity.Category;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategories(CategoryRequest categories);
    List<CategoryResponse> getCategories();
    Category getCategoryById(Long categoryId);
}
