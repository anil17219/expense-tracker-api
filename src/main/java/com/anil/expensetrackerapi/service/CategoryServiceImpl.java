package com.anil.expensetrackerapi.service;

import com.anil.expensetrackerapi.dto.CategoryDTO;
import com.anil.expensetrackerapi.dto.UserDTO;
import com.anil.expensetrackerapi.entity.CategoryEntity;
import com.anil.expensetrackerapi.entity.User;
import com.anil.expensetrackerapi.exceptions.ItemExistsException;
import com.anil.expensetrackerapi.exceptions.ResourceNotFoundException;
import com.anil.expensetrackerapi.io.CategoryRequest;
import com.anil.expensetrackerapi.repository.CategoryRepository;
import com.anil.expensetrackerapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Autowired
    private final UserService userService;

    /**
     * This is for reading the categories from database
     * @return list
     * */
    @Override
    public List<CategoryDTO> getAllCategories() {
        List<CategoryEntity> list =  categoryRepository.findByUserId(userService.getLoggedInUser().getId());
        return list.stream().map(categoryEntity -> mapToDTO(categoryEntity)).collect(Collectors.toList());
    }

    /**
     * This is for creating new category
     * @param categoryDTO
     * @return CategoryDTO
     * */
    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
       boolean isCategoryPresent =  categoryRepository.existsByNameAndUserId(categoryDTO.getName(), userService.getLoggedInUser().getId());
        if (isCategoryPresent) {
            throw new ItemExistsException("Category is already present for the name " + categoryDTO.getName());
        }
        CategoryEntity newCategory =  mapToEntity(categoryDTO);
        newCategory =  categoryRepository.save(newCategory);
        return mapToDTO(newCategory);
    }

    /**
     * This is for deleting category from database
     * @param categoryId
     * */
    @Override
    public void deleteCategory(String categoryId) {
        Optional<CategoryEntity> optionalCategory = categoryRepository.findByUserIdAndCategoryId(userService.getLoggedInUser().getId(), categoryId);

        if (!optionalCategory.isPresent()) {
            throw new ResourceNotFoundException("Category not found for the id" + categoryId);
        }
        categoryRepository.delete(optionalCategory.get());
    }

    /**
     * Mapper method to convert dto to category Entity
     * @param categoryDTO
     * @return CategoryEntity
     * */
    private CategoryEntity mapToEntity(CategoryDTO categoryDTO) {
        return CategoryEntity.builder()
                .name(categoryDTO.getName())
                .description(categoryDTO.getDescription())
                .categoryIcon(categoryDTO.getCategoryIcon())
                .categoryId(UUID.randomUUID().toString())
                .user(userService.getLoggedInUser())
                .build();
    }

    /**
     * Mapper method to convert category Entity to category DTO
     * @param categoryEntity
     * @return CategoryDTO
     * */
    private CategoryDTO mapToDTO(CategoryEntity categoryEntity) {
        return CategoryDTO.builder()

                .categoryId(categoryEntity.getCategoryId())
                .name(categoryEntity.getName())
                .description(categoryEntity.getDescription())
                .categoryIcon(categoryEntity.getCategoryIcon())
                .createdAt(categoryEntity.getCreatedAt())
                .updatedAt(categoryEntity.getUpdatedAt())
                .user(mapToUserDTO(categoryEntity.getUser()))
                .build();


    }

    /**
     * Mapper method to convert user entity to user DTO
     * @param user
     * @return UserDTO
     * */
    private UserDTO mapToUserDTO(User user) {
        return UserDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

}
