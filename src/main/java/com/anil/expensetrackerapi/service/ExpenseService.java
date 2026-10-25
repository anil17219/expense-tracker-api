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
    List<ExpenseDTO> readyByCategory(String category, Pageable page);
    List<ExpenseDTO> readByName(String keyword, Pageable page);
    List<ExpenseDTO> readByDate(Date startDate, Date endDate, Pageable page);
}
