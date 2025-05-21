package com.expenseTracking.application.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.YearMonth;

// Budget.java
@Entity
@Table(name = "budgets")
@Data
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "budget_year_month", nullable = false)
    private String yearMonth;  // Changed from YearMonth to String

    @Column(nullable = false)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}