package com.anil.expensetrackerapi.controller;

import com.anil.expensetrackerapi.dto.CategoryDTO;
import com.anil.expensetrackerapi.io.CategoryRequest;
import com.anil.expensetrackerapi.io.CategoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @PostMapping
    public CategoryResponse createCategory(@RequestBody CategoryRequest categoryRequest){
        CategoryDTO categoryDTO =  mapToDTO(categoryRequest);
    }

    private CategoryDTO mapToDTO(CategoryRequest categoryRequest) {
        return  CategoryDTO.builder()
                .name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .categoryIcon(categoryRequest.getCategoryIcon())
                .build();
    }
}
