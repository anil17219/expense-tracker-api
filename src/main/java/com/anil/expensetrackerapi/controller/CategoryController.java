package com.anil.expensetrackerapi.controller;

import com.anil.expensetrackerapi.dto.CategoryDTO;
import com.anil.expensetrackerapi.entity.CategoryEntity;
import com.anil.expensetrackerapi.io.CategoryRequest;
import com.anil.expensetrackerapi.io.CategoryResponse;
import com.anil.expensetrackerapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public CategoryResponse createCategory(@RequestBody CategoryRequest categoryRequest){
        CategoryDTO categoryDTO =  mapToDTO(categoryRequest);
        categoryDTO =  categoryService.saveCategory(categoryDTO);
        return mapToResponse(categoryDTO);
    }

    private CategoryResponse mapToResponse(CategoryDTO categoryDTO) {
        return CategoryResponse.builder()
                .categoryIcon(categoryDTO.getCategoryId())
                .name(categoryDTO.getName())
                .description(categoryDTO.getDescription())
                .categoryIcon(categoryDTO.getCategoryIcon())
                .createdAt(categoryDTO.getCreatedAt())
                .updatedAt(categoryDTO.getUpdatedAt())
                .build();
    }

    private CategoryDTO mapToDTO(CategoryRequest categoryRequest) {
        return  CategoryDTO.builder()
                .name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .categoryIcon(categoryRequest.getCategoryIcon())
                .build();
    }
}
