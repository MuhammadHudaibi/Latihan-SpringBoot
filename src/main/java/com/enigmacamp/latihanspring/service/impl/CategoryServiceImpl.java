package com.enigmacamp.latihanspring.service.impl;

import com.enigmacamp.latihanspring.dto.request.CategoryRequest;
import com.enigmacamp.latihanspring.dto.response.CategoryResponse;
import com.enigmacamp.latihanspring.entity.Category;
import com.enigmacamp.latihanspring.repository.CategoryRepository;
import com.enigmacamp.latihanspring.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse createCategories(CategoryRequest categories) {
        Category category = new Category();
        category.setName(categories.getName());

        categoryRepository.save(category);

        return mapToResponse(category);
    }

    @Override
    public List<CategoryResponse> getCategories() {
        return categoryRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
    }

    private CategoryResponse mapToResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
