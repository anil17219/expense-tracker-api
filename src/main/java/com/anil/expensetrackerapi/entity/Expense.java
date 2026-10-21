package com.anil.expensetrackerapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.dialect.lock.internal.TimesTenLockingSupport;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.concurrent.TimeoutException;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_expenses")
public class Expense {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "expense_name")
    @NotBlank(message = "Expense message must not be null")
    @Size(min = 3, message = "Expense name must be at least 3 characters")
    private String name;

    private String description;

    @Column(name = "expense_amount")
    @NotNull(message = "Expense amount should not be null")
    private BigDecimal amount;

    @NotBlank(message = "Category should not be null")
    private String category;

    @NotNull(message = "Date must not be nulll")
    private Date date;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private Timestamp updatedAt;
}
