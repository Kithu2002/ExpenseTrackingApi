package com.expenseTracking.application.dto;


import lombok.Data;
import java.math.BigDecimal;
import java.util.Map;

@Data
public class ExpenseSummaryDTO {
    private BigDecimal totalAmount;
    private Map<String, BigDecimal> amountByCategory;
    private Integer totalCount;
    private BigDecimal budget;
    private BigDecimal remainingBudget;
    private boolean budgetExceeded;
}