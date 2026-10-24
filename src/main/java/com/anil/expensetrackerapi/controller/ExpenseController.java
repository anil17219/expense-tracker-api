package com.anil.expensetrackerapi.controller;

import com.anil.expensetrackerapi.dto.CategoryDTO;
import com.anil.expensetrackerapi.dto.ExpenseDTO;
import com.anil.expensetrackerapi.entity.Expense;
import com.anil.expensetrackerapi.io.CategoryRequest;
import com.anil.expensetrackerapi.io.CategoryResponse;
import com.anil.expensetrackerapi.io.ExpenseRequest;
import com.anil.expensetrackerapi.io.ExpenseResponse;
import com.anil.expensetrackerapi.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;



import java.sql.Date;
import java.util.List;

@RestController
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/expenses")
    public List<Expense> getAllExpenses(Pageable page) {
        return  expenseService.getAllExpenses(page).toList();
    }

    @GetMapping("/expenses/{id}")
    public Expense getExpenseById(@PathVariable Long id){
        return expenseService.getExpenseById(id);
    }

    @DeleteMapping("/expenses")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteExpenseById(@RequestParam Long id){
        expenseService.deleteExpenseById(id);
    }

    @PostMapping("/expenses")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ExpenseResponse saveExpenseDetails(@Valid @RequestBody ExpenseRequest expenseRequest) {
        ExpenseDTO  expenseDTO =  mapToDTO(expenseRequest);
        expenseDTO =  expenseService.saveExpenseDetails(expenseDTO);
         return mapToResponse(expenseDTO);
    }

    private ExpenseResponse mapToResponse(ExpenseDTO expenseDTO) {
        return ExpenseResponse.builder()
                .expenseId(expenseDTO.getExpenseId())
                .name(expenseDTO.getName())
                .description(expenseDTO.getDescription())
                .amount(expenseDTO.getAmount())
                .date(expenseDTO.getDate())
                .category(mapToCategoryResponse(expenseDTO.getCategoryDTO()))
                .createdAt(expenseDTO.getCreatedAt())
                .updatedAt(expenseDTO.getUpdatedAt())
                .build();
    }

    private CategoryResponse mapToCategoryResponse(CategoryDTO categoryDTO) {
        return CategoryResponse.builder()
                .categoryId(categoryDTO.getCategoryId())
                .name(categoryDTO.getName())
                .build();
    }

    private ExpenseDTO mapToDTO(@Valid ExpenseRequest expenseRequest) {
        return ExpenseDTO.builder()
                .name(expenseRequest.getName())
                .description(expenseRequest.getDescription())
                .amount(expenseRequest.getAmount())
                .date(expenseRequest.getDate())
                .categoryId(expenseRequest.getCategoryId())
                .build();
    }

    @PutMapping("/expenses/{id}")
    public Expense updateExpenseDetails(@RequestBody Expense expense, @PathVariable Long id) {
        return expenseService.updateExpenseDetails(id, expense);
    }

    @GetMapping("/expenses/category")
    public List<Expense> getExpensesByCategory(@RequestParam String category, Pageable page) {
        return expenseService.readyByCategory(category, page);
    }

    @GetMapping("/expenses/name")
    public List<Expense> getExpensesByName(@RequestParam String keyword, Pageable page) {
        return expenseService.readByName(keyword, page);
    }

    @GetMapping("/expenses/date")
    public List<Expense> getExpensesByDate(@RequestParam (required = false) Date startDate, @RequestParam (required = false) Date endDate, Pageable page) {
        return expenseService.readByDate(startDate, endDate, page);
    }
}
