package com.expenseTracking.application.service;

import com.expenseTracking.application.exception.ResourceNotFoundException;
import com.expenseTracking.application.model.Category;
import com.expenseTracking.application.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    @Transactional
    public Category createCategory(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new IllegalArgumentException("Category with name already exists: " + category.getName());
        }
        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(Long id, Category category) {
        Category existingCategory = getCategoryById(id);

        if (!existingCategory.getName().equals(category.getName())
                && categoryRepository.existsByName(category.getName())) {
            throw new IllegalArgumentException("Category with name already exists: " + category.getName());
        }

        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription());

        return categoryRepository.save(existingCategory);
    }

    @Transactional
    public void deleteCategory(Long id) {
        Category category = getCategoryById(id);
        if (!category.getExpenses().isEmpty()) {
            throw new IllegalStateException("Cannot delete category with associated expenses");
        }
        categoryRepository.delete(category);
    }
}