package com.krushisevakendra.billing.category.service;

import com.krushisevakendra.billing.category.dto.CategoryRequest;
import com.krushisevakendra.billing.category.dto.CategoryResponse;
import com.krushisevakendra.billing.category.entity.Category;
import com.krushisevakendra.billing.category.mapper.CategoryMapper;
import com.krushisevakendra.billing.category.repository.CategoryRepository;
import com.krushisevakendra.billing.exception.DuplicateResourceException;
import com.krushisevakendra.billing.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements  CategoryService{

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Override
    public Page<CategoryResponse> getAllCategories(int page, int size, String sortBy, String direction) {

        //Create Sort Object
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        //Create Pageable
        Pageable pageable = PageRequest.of(page, size, sort);

        //Fetch Data
        Page<Category> categoryPage = categoryRepository.findByActiveTrue(pageable);

        return categoryPage.map(categoryMapper::toResponse);

    }

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {


        // Check duplicate category
        if (categoryRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Category already exists.");
        }

        // Convert DTO to Entity
        Category category = categoryMapper.toEntity(request);

        // Save Entity
        Category savedCategory = categoryRepository.save(category);

        // Convert Entity to Response DTO
        return categoryMapper.toResponse(savedCategory);
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {

        Category category = categoryRepository.findByIdAndActiveTrue(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found with id : " + id));

        return categoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {

        //Find Existing category
        Category category = categoryRepository.findByIdAndActiveTrue(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found with id : "+ id));

        //Check duplicate name
        if(!category.getName().equalsIgnoreCase(request.getName())
        && categoryRepository.existsByName(request.getName())){
            throw new DuplicateResourceException("Category Already Exists.");

        }

        category.setName(request.getName());
        category.setDescription(request.getDescription());

        Category updatedCategory = categoryRepository.save(category);

        return categoryMapper.toResponse(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {

        Category category = categoryRepository.findByIdAndActiveTrue(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category Not Found with id : "+ id));

        category.setActive(false);

        categoryRepository.save(category);
    }
}
