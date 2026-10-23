package com.anil.expensetrackerapi.service;

import com.anil.expensetrackerapi.dto.CategoryDTO;

import java.util.List;

/**
 * Service interface for managing the categories
 * @author Anil Pal
 * */
public interface CategoryService {
    /**
     * This is for reading the categories from database
     * @return list
     * */
    List<CategoryDTO> getAllCategories();

    /**
     * This is for creating new category
     * @param categoryDTO
     * @return CategoryDTO
     * */
    CategoryDTO saveCategory(CategoryDTO categoryDTO);

    /**
     * This is for deleting category from database
     * @param categoryId
     * */
    void deleteCategory(String categoryId);
}
