package com.anil.expensetrackerapi.service;

import com.anil.expensetrackerapi.dto.ExpenseDTO;
import com.anil.expensetrackerapi.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.sql.Date;
import java.util.List;

public interface ExpenseService {
    List<ExpenseDTO> getAllExpenses(Pageable page);
    Expense getExpenseById(String id);
    void deleteExpenseById(String id);
    ExpenseDTO saveExpenseDetails(ExpenseDTO expenseDTO);
    Expense updateExpenseDetails(String id, Expense expense);
    List<Expense> readyByCategory(String category, Pageable page);
    List<Expense> readByName(String keyword, Pageable page);
    List<Expense> readByDate(Date startDate, Date endDate, Pageable page);
}
