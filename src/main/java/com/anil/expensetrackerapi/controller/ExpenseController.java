package com.anil.expensetrackerapi.controller;

import com.anil.expensetrackerapi.dto.CategoryDTO;
import com.anil.expensetrackerapi.dto.ExpenseDTO;
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
import java.util.stream.Collectors;

@RestController
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/expenses")
    public List<ExpenseResponse> getAllExpenses(Pageable page) {
        List<ExpenseDTO> listOfExpenses =  expenseService.getAllExpenses(page);
        return listOfExpenses.stream().map(expenseDTO -> mapToResponse(expenseDTO)).collect(Collectors.toList());
    }

    @GetMapping("/expenses/{expenseId}")
    public ExpenseResponse getExpenseById(@PathVariable String expenseId){
        ExpenseDTO expenseDTO = expenseService.getExpenseById(expenseId);
        return mapToResponse(expenseDTO);
    }

    @DeleteMapping("/expenses")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteExpenseById(@RequestParam String expenseId){
        expenseService.deleteExpenseById(expenseId);
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

    @PutMapping("/expenses/{expenseId}")
    public ExpenseResponse updateExpenseDetails(@RequestBody ExpenseRequest expenseRequest, @PathVariable String expenseId) {
        ExpenseDTO updatedExpense = mapToDTO(expenseRequest);
        updatedExpense =  expenseService.updateExpenseDetails(expenseId, updatedExpense);
        return mapToResponse(updatedExpense);
    }

    @GetMapping("/expenses/category")
    public List<ExpenseResponse> getExpensesByCategory(@RequestParam String category, Pageable page) {
        List<ExpenseDTO> list =  expenseService.readyByCategory(category, page);
        return list.stream().map(expenseDTO -> mapToResponse(expenseDTO)).collect(Collectors.toList());
    }

    @GetMapping("/expenses/name")
    public List<ExpenseResponse> getExpensesByName(@RequestParam String keyword, Pageable page) {
        List<ExpenseDTO> list = expenseService.readByName(keyword, page);
        return list.stream().map(expenseDTO -> mapToResponse(expenseDTO)).collect(Collectors.toList());
    }

    @GetMapping("/expenses/date")
    public List<ExpenseResponse> getExpensesByDate(@RequestParam (required = false) Date startDate, @RequestParam (required = false) Date endDate, Pageable page) {
        List<ExpenseDTO> list = expenseService.readByDate(startDate, endDate, page);
        return list.stream().map(expenseDTO -> mapToResponse(expenseDTO)).collect(Collectors.toList());
    }
}
