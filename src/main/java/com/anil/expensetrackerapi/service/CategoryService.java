package com.anil.expensetrackerapi.service;

import com.anil.expensetrackerapi.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();
    CategoryDTO saveCategory(CategoryDTO categoryDTO);
    void deleteCategory(String categoryId);
}
