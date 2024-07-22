package com.hps.productservice.service;

import com.hps.productservice.dto.CategoryRequest;
import com.hps.productservice.dto.CategoryResponse;
import com.hps.productservice.entity.Category;
import com.hps.productservice.mapper.CategoryMapper;
import com.hps.productservice.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public Long createCategory(CategoryRequest request) {
        var category = categoryMapper.toCategory(request);
        return categoryRepository.save(category).getId();
    }

    public CategoryResponse getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(categoryMapper::toCategoryResponse)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with the ID:: " + categoryId));
    }

    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toCategoryResponse)
                .collect(Collectors.toList());
    }

    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    public Long updateCategory(Long categoryId, CategoryRequest request) {
        var category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with the ID:: " + categoryId));
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        return categoryRepository.save(category).getId();
    }
}
