package com.krushisevakendra.billing.product.service;

import com.krushisevakendra.billing.product.dto.ProductRequestDto;
import com.krushisevakendra.billing.product.dto.ProductResponseDto;
import com.krushisevakendra.billing.product.entity.Product;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface ProductService {

    ProductResponseDto createProduct(ProductRequestDto requestDto);

    Page<ProductResponseDto> getAllProducts(Pageable pageable);

    ProductResponseDto getProductById(Long id);

    ProductResponseDto updateProduct(Long id, ProductRequestDto requestDto);

    void deleteProduct(Long id);


}
