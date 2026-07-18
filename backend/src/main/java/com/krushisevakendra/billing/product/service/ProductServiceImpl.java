package com.krushisevakendra.billing.product.service;

import com.krushisevakendra.billing.category.entity.Category;
import com.krushisevakendra.billing.category.repository.CategoryRepository;
import com.krushisevakendra.billing.exception.DuplicateResourceException;
import com.krushisevakendra.billing.exception.ResourceNotFoundException;
import com.krushisevakendra.billing.product.dto.ProductRequestDto;
import com.krushisevakendra.billing.product.dto.ProductResponseDto;
import com.krushisevakendra.billing.product.entity.Product;
import com.krushisevakendra.billing.product.mapper.ProductMapper;
import com.krushisevakendra.billing.product.repository.ProductRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;



@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;


    @Override
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {

        //duplicate validation
        if (productRepository.existsByNameIgnoreCaseAndActiveTrue(requestDto.getName())) {

            throw new DuplicateResourceException(
                    "Product already exists with name: " + requestDto.getName()
            );
        }

        //Category Validation
        Category category = categoryRepository.
                findByIdAndActiveTrue(requestDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category Not Found With Id: "+ requestDto.getCategoryId()));

        //Prodect object
        Product product = productMapper.toEntity(requestDto);
        product.setCategory(category);
        //Save Product
        Product savedProduct = productRepository.save(product);

        return productMapper.toResponse(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDto> getAllProducts(Pageable pageable) {

        //Fetch Products from database
        Page<Product> products = productRepository.findByActiveTrue(pageable);

        return products.map(productMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDto getProductById(Long id) {

        Product product = productRepository.findByIdAndActiveTrue(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product not found with id : " + id));
        return productMapper.toResponse(product);
    }

    @Override
    @Transactional
    public ProductResponseDto updateProduct(Long id, ProductRequestDto requestDto) {
        //1. Find existing active product
        Product product = productRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with : "+ id));

        // 2. Check duplicate product name
        if(!product.getName().equalsIgnoreCase(requestDto.getName()) &&
                productRepository.existsByNameIgnoreCase(requestDto.getName())){
            throw new ResourceNotFoundException("Product already exists with name : " + requestDto.getName());
        }

        // 3. Find category
        Category category = categoryRepository.findByIdAndActiveTrue(requestDto.getCategoryId())
                .orElseThrow(()-> new ResourceNotFoundException("Category not found with id : " + requestDto.getCategoryId()));

        //4. Update fields
        product.setName(requestDto.getName());
        product.setDescription(requestDto.getDescription());
        product.setPurchasePrice(requestDto.getPurchasePrice());
        product.setSellingPrice(requestDto.getSellingPrice());
        product.setStockQuantity(requestDto.getStockQuantity());
        product.setUnit(requestDto.getUnit());
        product.setCategory(category);

        //5. Save updated product
        Product updatedProduct = productRepository.save(product);

        // 6. Return response
        return productMapper.toResponse(updatedProduct);

    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {

        Product product = productRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: "+ id));

        product.setActive(false);

        productRepository.save(product);

    }
}
