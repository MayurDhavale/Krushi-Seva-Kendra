package com.krushisevakendra.billing.category.controller;

import com.krushisevakendra.billing.category.dto.CategoryRequest;
import com.krushisevakendra.billing.category.dto.CategoryResponse;
import com.krushisevakendra.billing.category.service.CategoryService;
import com.krushisevakendra.billing.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    //Create Category
    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@Valid @RequestBody CategoryRequest request){

        CategoryResponse response = categoryService.createCategory(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Category created successfully.",
                        HttpStatus.CREATED.value(),
                        response
                ));
    }

    //Get All categories
    @GetMapping
    public ResponseEntity<ApiResponse<Page<CategoryResponse>>> getAllCategories(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String direction
    ){
        Page<CategoryResponse> categories = categoryService.getAllCategories(
                page, size, sortBy, direction);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Categories fetched successfully.",
                        HttpStatus.OK.value(),
                        categories
                )
        );
    }

    //Get Category By ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(@PathVariable Long id){

        CategoryResponse response = categoryService.getCategoryById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Category Fetched successfully",
                        HttpStatus.OK.value(),
                        response
                )
        );
    }

    //Update category
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request) {

        CategoryResponse response = categoryService.updateCategory(id,request);


        return ResponseEntity.ok(
                ApiResponse.success(
                        "Category updated successfully.",
                        HttpStatus.OK.value(),
                        response
                )
        );
    }

}
