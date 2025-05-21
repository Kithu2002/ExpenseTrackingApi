package com.expenseTracking.application.service;

import com.expenseTracking.application.dto.BudgetDTO;
import com.expenseTracking.application.exception.ResourceNotFoundException;
import com.expenseTracking.application.model.Budget;
import com.expenseTracking.application.model.Category;
import com.expenseTracking.application.model.User;
import com.expenseTracking.application.repository.BudgetRepository;
import com.expenseTracking.application.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;

    @Transactional
    public BudgetDTO setBudget(BudgetDTO budgetDTO) {
        User currentUser = userService.getCurrentUser();

        Budget budget;
        if (budgetDTO.getCategoryId() == null) {
            // Overall monthly budget
            Optional<Budget> existingBudget = budgetRepository.findByUserAndYearMonth(
                    currentUser,
                    budgetDTO.getYearMonth()
            );

            budget = existingBudget.orElse(new Budget());
        } else {
            // Category-specific budget
            Category category = categoryRepository.findById(budgetDTO.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

            Optional<Budget> existingBudget = budgetRepository.findByUserAndYearMonthAndCategoryId(
                    currentUser,
                    budgetDTO.getYearMonth(),
                    budgetDTO.getCategoryId()
            );

            budget = existingBudget.orElse(new Budget());
            budget.setCategory(category);
        }

        budget.setUser(currentUser);
        budget.setYearMonth(budgetDTO.getYearMonth());
        budget.setAmount(budgetDTO.getAmount());

        budget = budgetRepository.save(budget);
        return convertToDTO(budget);
    }

    @Transactional(readOnly = true)
    public BigDecimal getBudgetAmount(User user, String yearMonth) {
        Optional<Budget> budget = budgetRepository.findByUserAndYearMonth(user, yearMonth);
        return budget.map(Budget::getAmount).orElse(null);
    }
    private int[] extractYearMonth(String yearMonth) {
        String[] parts = yearMonth.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        return new int[]{year, month};
    }


    private BudgetDTO convertToDTO(Budget budget) {
        BudgetDTO dto = new BudgetDTO();
        dto.setId(budget.getId());
        dto.setYearMonth(budget.getYearMonth());
        dto.setAmount(budget.getAmount());
        if (budget.getCategory() != null) {
            dto.setCategoryId(budget.getCategory().getId());
        }
        return dto;
    }
}