package com.expenseTracker.application.Repository;

import com.expenseTracker.application.Entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense,Long> {
}
