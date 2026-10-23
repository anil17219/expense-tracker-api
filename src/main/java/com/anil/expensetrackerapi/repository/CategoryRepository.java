package com.anil.expensetrackerapi.repository;

import com.anil.expensetrackerapi.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    /**
     * Finder method to retrieve the category by id
     * @param userId
     * @return list
     * */
    List<CategoryEntity> findByUserId(Long userId);

    /**
     * Finder method to fetch the category by user id
     * @param id, categoryId
     * @return Optional<CategoryEntity>
     * */
    Optional<CategoryEntity> findByUserIdAndCategoryId(Long id, String categoryId);
}
