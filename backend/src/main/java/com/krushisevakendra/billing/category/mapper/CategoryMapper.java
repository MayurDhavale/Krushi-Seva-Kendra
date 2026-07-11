package com.krushisevakendra.billing.category.mapper;

import com.krushisevakendra.billing.category.dto.CategoryRequest;
import com.krushisevakendra.billing.category.dto.CategoryResponse;
import com.krushisevakendra.billing.category.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequest request){

        return Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    public CategoryResponse toResponse(Category category){

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .active(category.getActive())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }
}
