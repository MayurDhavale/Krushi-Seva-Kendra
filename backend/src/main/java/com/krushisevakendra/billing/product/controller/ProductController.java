package com.krushisevakendra.billing.product.controller;

import com.krushisevakendra.billing.common.response.ApiResponse;
import com.krushisevakendra.billing.product.dto.ProductRequestDto;
import com.krushisevakendra.billing.product.dto.ProductResponseDto;
import com.krushisevakendra.billing.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDto>> createProduct(
            @Valid @RequestBody ProductRequestDto requestDto){

        ProductResponseDto response = productService.createProduct(requestDto);

     return ResponseEntity.status(HttpStatus.CREATED).body(
             ApiResponse.success(
                     "Product created successfully.",
                     HttpStatus.CREATED.value(),
                     response
             )
     );
    }


    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProductResponseDto>>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ProductResponseDto> products = productService.getAllProducts(pageable);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Products fetched successfully.",
                        HttpStatus.OK.value(),
                        products
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> getProductById(
            @PathVariable Long id
    ){
        ProductResponseDto response = productService.getProductById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Product fetched successfully.",
                        HttpStatus.OK.value(),
                        response
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDto requestDto
    ){

        ProductResponseDto response = productService.updateProduct(id,requestDto);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Product updated successfully.",
                        HttpStatus.OK.value(),
                        response
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id){

        productService.deleteProduct(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Product deleted successfully.",
                        HttpStatus.OK.value(),
                        null
                )
        );
    }
}
