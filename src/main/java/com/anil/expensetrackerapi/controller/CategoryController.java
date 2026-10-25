package com.anil.expensetrackerapi.controller;

import com.anil.expensetrackerapi.dto.CategoryDTO;
import com.anil.expensetrackerapi.entity.CategoryEntity;
import com.anil.expensetrackerapi.io.CategoryRequest;
import com.anil.expensetrackerapi.io.CategoryResponse;
import com.anil.expensetrackerapi.mappers.CategoryMapper;
import com.anil.expensetrackerapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 * This Controller is for managing the categories
 * @author Anil Pal
 * */
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    private final CategoryMapper categoryMapper;

    /***
     * API for creating the category
     * @param categoryRequest
     * @return categoryResponse
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CategoryResponse createCategory(@RequestBody CategoryRequest categoryRequest){
        CategoryDTO categoryDTO =  categoryMapper.mapToCategoryDTO(categoryRequest);
        categoryDTO =  categoryService.saveCategory(categoryDTO);
        return categoryMapper.mapToCategoryResponse(categoryDTO);
    }

    /***
     * API for reading the categories
     * @return list
     */
    @GetMapping
    public List<CategoryResponse> readCategories(){
        List<CategoryDTO> listOfCategories =  categoryService.getAllCategories();
        return listOfCategories.stream().map(categoryDTO -> categoryMapper.mapToCategoryResponse(categoryDTO)).collect(Collectors.toList());
    }

    /**
     * API for deleting the category
     * @param categoryId
     * */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable String categoryId){
        categoryService.deleteCategory(categoryId);
    }

}
