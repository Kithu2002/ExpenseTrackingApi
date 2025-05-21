package com.expenseTracking.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.YearMonth;

@Data
public class BudgetDTO {
    private Long id;

    @NotBlank(message = "Year and month are required")
    @Pattern(regexp = "^\\d{4}-\\d{2}$", message = "Year-Month must be in the format YYYY-MM")
    private String yearMonth;  // Changed from YearMonth to String

    @NotNull(message = "Budget amount is required")
    @Positive(message = "Budget must be positive")
    private BigDecimal amount;

    private Long categoryId;
}