package com.expenseTracking.application.repository;

import com.expenseTracking.application.model.Budget;
import com.expenseTracking.application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.YearMonth;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Optional<Budget> findByUserAndYearMonth(User user, String yearMonth);

    Optional<Budget> findByUserAndYearMonthAndCategoryId(
            User user,
            String yearMonth,
            Long categoryId
    );
}