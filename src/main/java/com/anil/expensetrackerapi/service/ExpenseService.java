package com.anil.expensetrackerapi.service;

import com.anil.expensetrackerapi.dto.ExpenseDTO;
import com.anil.expensetrackerapi.entity.Expense;
import org.springframework.data.domain.Pageable;


import java.sql.Date;
import java.util.List;

public interface ExpenseService {
    List<ExpenseDTO> getAllExpenses(Pageable page);
    ExpenseDTO getExpenseById(String id);
    void deleteExpenseById(String id);
    ExpenseDTO saveExpenseDetails(ExpenseDTO expenseDTO);
    ExpenseDTO updateExpenseDetails(String expenseId, ExpenseDTO expenseDTO);
    List<Expense> readyByCategory(String category, Pageable page);
    List<Expense> readByName(String keyword, Pageable page);
    List<Expense> readByDate(Date startDate, Date endDate, Pageable page);
}
