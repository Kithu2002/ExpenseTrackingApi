package com.expenseTracker.application.Repository;

import com.expenseTracker.application.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
