package com.anil.expensetrackerapi.repository;

import com.anil.expensetrackerapi.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // SELECT * FROM tbl_expenses WHERE category = ?
    Page<Expense> findByUserIdAndCategory(Long userId, String category, Pageable page);

    Page<Expense> findByUserIdAndCategoryId(Long userId, Long categoryId, Pageable page);

    // SELECT * FROM tbl_expenses WHERE name LIKE '%keyword%'
    Page<Expense> findByUserIdAndNameContaining(Long userId, String keyword, Pageable page);

    // SELECT * FROM tbl_expenses WHERE date BETWEEN 'startDate' AND 'endDate'
    Page<Expense> findByUserIdAndDateBetween(Long userId, Date startDate, Date endDate, Pageable page);

    // SELECT * FROM tbl_expenses WHERE userId matches
    Page<Expense> findByUserId(Long userId, Pageable page);

    // SELECT * FROM tbl_expenses WHERE userId = ? AND id = ?
    Optional<Expense> findByUserIdAndExpenseId(Long userId, String expenseId);

}
