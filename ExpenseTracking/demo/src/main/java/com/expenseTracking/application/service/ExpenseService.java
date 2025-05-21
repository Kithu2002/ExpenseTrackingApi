package com.expenseTracking.application.service;


import com.expenseTracking.application.dto.ExpenseDTO;
import com.expenseTracking.application.dto.ExpenseRequest;
import com.expenseTracking.application.dto.ExpenseSummaryDTO;
import com.expenseTracking.application.exception.AccessDeniedException;
import com.expenseTracking.application.exception.ResourceNotFoundException;
import com.expenseTracking.application.model.Category;
import com.expenseTracking.application.model.Expense;
import com.expenseTracking.application.model.User;
import com.expenseTracking.application.repository.CategoryRepository;
import com.expenseTracking.application.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final BudgetService budgetService;

    @Transactional(readOnly = true)
    public List<ExpenseDTO> getAllExpenses() {
        User currentUser = userService.getCurrentUser();
        return expenseRepository.findByUserOrderByDateDesc(currentUser)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ExpenseDTO getExpenseById(Long id) {
        User currentUser = userService.getCurrentUser();
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));

        if (!expense.getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You do not have permission to access this expense");
        }

        return convertToDTO(expense);
    }

    @Transactional
    public ExpenseDTO createExpense(ExpenseRequest expenseRequest) {
        User currentUser = userService.getCurrentUser();
        Category category = categoryRepository.findById(expenseRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Expense expense = new Expense();
        expense.setDescription(expenseRequest.getDescription());
        expense.setAmount(expenseRequest.getAmount());
        expense.setDate(expenseRequest.getDate() != null ? expenseRequest.getDate() : LocalDateTime.now());
        expense.setUser(currentUser);
        expense.setCategory(category);

        expense = expenseRepository.save(expense);
        return convertToDTO(expense);
    }

    @Transactional
    public ExpenseDTO updateExpense(Long id, ExpenseRequest expenseRequest) {
        User currentUser = userService.getCurrentUser();
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));

        if (!expense.getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You do not have permission to update this expense");
        }

        Category category = categoryRepository.findById(expenseRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        expense.setDescription(expenseRequest.getDescription());
        expense.setAmount(expenseRequest.getAmount());
        if (expenseRequest.getDate() != null) {
            expense.setDate(expenseRequest.getDate());
        }
        expense.setCategory(category);

        expense = expenseRepository.save(expense);
        return convertToDTO(expense);
    }

    @Transactional
    public void deleteExpense(Long id) {
        User currentUser = userService.getCurrentUser();
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));

        if (!expense.getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You do not have permission to delete this expense");
        }

        expenseRepository.delete(expense);
    }

    @Transactional(readOnly = true)
    public List<ExpenseDTO> getExpensesByMonth(int year, int month) {
        User currentUser = userService.getCurrentUser();
        return expenseRepository.findByUserAndYearAndMonth(currentUser, year, month)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExpenseDTO> getExpensesByCategory(Long categoryId) {
        User currentUser = userService.getCurrentUser();
        return expenseRepository.findByUserAndCategory(currentUser, categoryId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ExpenseSummaryDTO getSummary() {
        User currentUser = userService.getCurrentUser();
        List<Expense> expenses = expenseRepository.findByUserOrderByDateDesc(currentUser);

        return createSummary(expenses);
    }

    @Transactional(readOnly = true)
    public ExpenseSummaryDTO getMonthlySummary(int year, int month) {
        User currentUser = userService.getCurrentUser();
        List<Expense> expenses = expenseRepository.findByUserAndYearAndMonth(currentUser, year, month);

        ExpenseSummaryDTO summary = createSummary(expenses);

        // Add budget information - using String format instead of YearMonth
        String yearMonthString = String.format("%04d-%02d", year, month);
        BigDecimal budget = budgetService.getBudgetAmount(currentUser, yearMonthString);

        summary.setBudget(budget);

        if (budget != null && summary.getTotalAmount() != null) {
            BigDecimal remaining = budget.subtract(summary.getTotalAmount());
            summary.setRemainingBudget(remaining);
            summary.setBudgetExceeded(remaining.compareTo(BigDecimal.ZERO) < 0);
        }

        return summary;
    }

    private ExpenseSummaryDTO createSummary(List<Expense> expenses) {
        ExpenseSummaryDTO summary = new ExpenseSummaryDTO();

        BigDecimal total = expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, BigDecimal> byCategory = new HashMap<>();

        expenses.forEach(expense -> {
            String categoryName = expense.getCategory().getName();
            byCategory.put(
                    categoryName,
                    byCategory.getOrDefault(categoryName, BigDecimal.ZERO).add(expense.getAmount())
            );
        });

        summary.setTotalAmount(total);
        summary.setAmountByCategory(byCategory);
        summary.setTotalCount(expenses.size());

        return summary;
    }

    private ExpenseDTO convertToDTO(Expense expense) {
        ExpenseDTO dto = new ExpenseDTO();
        dto.setId(expense.getId());
        dto.setDescription(expense.getDescription());
        dto.setAmount(expense.getAmount());
        dto.setDate(expense.getDate());
        dto.setCategoryId(expense.getCategory().getId());
        dto.setCategoryName(expense.getCategory().getName());
        return dto;
    }
}