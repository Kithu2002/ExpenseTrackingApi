package com.expenseTracking.application.controller;

import com.expenseTracking.application.dto.BudgetDTO;
import com.expenseTracking.application.service.BudgetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
public class BudgetController {
    private final BudgetService budgetService;

    @PostMapping
    public ResponseEntity<BudgetDTO> setBudget(@Valid @RequestBody BudgetDTO budgetDTO) {
        return ResponseEntity.ok(budgetService.setBudget(budgetDTO));
    }
}