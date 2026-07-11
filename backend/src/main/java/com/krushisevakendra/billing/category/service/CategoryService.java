package com.krushisevakendra.billing.category.service;

import com.krushisevakendra.billing.category.dto.CategoryRequest;
import com.krushisevakendra.billing.category.dto.CategoryResponse;
import org.springframework.data.domain.Page;

public interface CategoryService {

    Page<CategoryResponse> getAllCategories(
            int page,
            int size,
            String sortBy,
            String direction
    );

    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse getCategoryById(Long id);

    CategoryResponse updateCategory(Long id, CategoryRequest request);
}
