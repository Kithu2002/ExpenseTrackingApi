package com.expenseTracker.application.Repository;

import com.expenseTracker.application.Entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget,Long> {
}
