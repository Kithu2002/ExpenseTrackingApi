package com.expenseTracking.application.repository;


import com.expenseTracking.application.model.Expense;
import com.expenseTracking.application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserOrderByDateDesc(User user);

    @Query("SELECT e FROM Expense e WHERE e.user = :user AND YEAR(e.date) = :year AND MONTH(e.date) = :month")
    List<Expense> findByUserAndYearAndMonth(
            @Param("user") User user,
            @Param("year") int year,
            @Param("month") int month
    );

    @Query("SELECT e FROM Expense e WHERE e.user = :user AND e.category.id = :categoryId")
    List<Expense> findByUserAndCategory(
            @Param("user") User user,
            @Param("categoryId") Long categoryId
    );

    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.user = :user AND YEAR(e.date) = :year AND MONTH(e.date) = :month")
    BigDecimal sumExpensesByUserAndYearAndMonth(
            @Param("user") User user,
            @Param("year") int year,
            @Param("month") int month
    );

    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.user = :user AND YEAR(e.date) = :year AND MONTH(e.date) = :month AND e.category.id = :categoryId")
    BigDecimal sumExpensesByUserAndYearAndMonthAndCategory(
            @Param("user") User user,
            @Param("year") int year,
            @Param("month") int month,
            @Param("categoryId") Long categoryId
    );
}
