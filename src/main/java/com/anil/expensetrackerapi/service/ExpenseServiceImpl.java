package com.anil.expensetrackerapi.service;

import com.anil.expensetrackerapi.dto.CategoryDTO;
import com.anil.expensetrackerapi.dto.ExpenseDTO;
import com.anil.expensetrackerapi.entity.CategoryEntity;
import com.anil.expensetrackerapi.entity.Expense;
import com.anil.expensetrackerapi.exceptions.ResourceNotFoundException;
import com.anil.expensetrackerapi.repository.CategoryRepository;
import com.anil.expensetrackerapi.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Page<Expense> getAllExpenses(Pageable page) {
        return expenseRepo.findByUserId(userService.getLoggedInUser().getId(), page);
    }

    @Override
    public Expense getExpenseById(Long id) {
        Optional<Expense> expense = expenseRepo.findByUserIdAndId(userService.getLoggedInUser().getId(), id);
        if (expense.isPresent()) {
             return expense.get();
        }
        throw new ResourceNotFoundException("Expense is not found for the id " + id);
    }

    @Override
    public void deleteExpenseById(Long id) {
        Expense expense = getExpenseById(id);
        expenseRepo.delete(expense);
    }

    @Override
    public ExpenseDTO saveExpenseDetails(ExpenseDTO expenseDTO) {
        // Check the existence of category
        Optional<CategoryEntity> optionalCategory = categoryRepository.findByUserIdAndCategoryId(userService.getLoggedInUser().getId(), expenseDTO.getCategoryId());
        if (!optionalCategory.isPresent()) {
            throw new ResourceNotFoundException("Category not found for the id " + expenseDTO.getCategoryId());
        }
        expenseDTO.setExpenseId(UUID.randomUUID().toString());
        // map to Entity Object
        Expense newExpense =  mapToEntity(expenseDTO);
        // save to the database
        newExpense.setCategory(optionalCategory.get());
        newExpense.setUser(userService.getLoggedInUser());
        newExpense =  expenseRepo.save(newExpense);

        // map to Response Object
        return mapToDTO(newExpense);
    }

    private ExpenseDTO mapToDTO(Expense newExpense) {
        return ExpenseDTO.builder()
                .expenseId(newExpense.getExpenseId())
                .name(newExpense.getName())
                .description(newExpense.getDescription())
                .amount(newExpense.getAmount())
                .date(newExpense.getDate())
                .createdAt(newExpense.getCreatedAt())
                .updatedAt(newExpense.getUpdatedAt())
                .categoryDTO(mapToCategoryDTO(newExpense.getCategory()))
                .build();
    }

    private CategoryDTO mapToCategoryDTO(CategoryEntity category) {
        return CategoryDTO.builder()
                .name(category.getName())
                .categoryId(category.getCategoryId())
                .build();
    }

    private Expense mapToEntity(ExpenseDTO expenseDTO) {
        return Expense.builder()
                .expenseId(expenseDTO.getExpenseId())
                .name(expenseDTO.getName())
                .description(expenseDTO.getDescription())
                .amount(expenseDTO.getAmount())
                .date(expenseDTO.getDate())
                .build();
    }

    @Override
    public Expense updateExpenseDetails(Long id, Expense expense) {
        Expense existingExpense = getExpenseById(id);
        existingExpense.setName(expense.getName() != null ? expense.getName() : existingExpense.getName());
        existingExpense.setDescription(expense.getDescription() != null ? expense.getDescription() : existingExpense.getDescription());
        existingExpense.setAmount(expense.getAmount() != null ? expense.getAmount() : existingExpense.getAmount());
        existingExpense.setCategory(expense.getCategory() != null ? expense.getCategory() : existingExpense.getCategory());
        existingExpense.setDate(expense.getDate() != null ? expense.getDate() : existingExpense.getDate());

        return expenseRepo.save(existingExpense);
    }

    @Override
    public List<Expense> readyByCategory(String category, Pageable page) {
        return expenseRepo.findByUserIdAndCategory(userService.getLoggedInUser().getId(), category, page).toList();
    }

    @Override
    public List<Expense> readByName(String keyword, Pageable page) {
        return expenseRepo.findByUserIdAndNameContaining(userService.getLoggedInUser().getId(), keyword, page).toList();
    }

    @Override
    public List<Expense> readByDate(Date startDate, Date endDate, Pageable page) {
        if (startDate == null) {
            startDate = new Date(0);
        }
        if (endDate == null) {
            endDate = new Date(System.currentTimeMillis());
        }

         return expenseRepo.findByUserIdAndDateBetween(userService.getLoggedInUser().getId(), startDate, endDate, page).toList();
    }


}
