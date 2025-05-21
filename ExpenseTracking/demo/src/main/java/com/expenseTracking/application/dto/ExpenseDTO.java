package com.expenseTracking.application.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ExpenseDTO {
    private Long id;
    private String description;
    private BigDecimal amount;
    private LocalDateTime date;
    private Long categoryId;
    private String categoryName;
}

