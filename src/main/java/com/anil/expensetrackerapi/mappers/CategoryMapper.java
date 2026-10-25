package com.anil.expensetrackerapi.mappers;

import com.anil.expensetrackerapi.dto.CategoryDTO;
import com.anil.expensetrackerapi.entity.CategoryEntity;
import com.anil.expensetrackerapi.io.CategoryRequest;
import com.anil.expensetrackerapi.io.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryEntity mapToCategoryEntity(CategoryDTO categoryDTO);

    CategoryDTO mapToCategoryDTO(CategoryEntity categoryEntity);

    @Mapping(target = "categoryIcon", source = "icon")
    CategoryDTO mapToCategoryDTO(CategoryRequest categoryRequest);

    CategoryResponse mapToCategoryResponse(CategoryDTO categoryDTO);
}
