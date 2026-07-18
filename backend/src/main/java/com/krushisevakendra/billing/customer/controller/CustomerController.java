package com.krushisevakendra.billing.customer.controller;

import ch.qos.logback.core.joran.spi.DefaultClass;
import com.krushisevakendra.billing.common.response.ApiResponse;
import com.krushisevakendra.billing.customer.dto.CustomerRequestDto;
import com.krushisevakendra.billing.customer.dto.CustomerResponseDto;
import com.krushisevakendra.billing.customer.entity.Customer;
import com.krushisevakendra.billing.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<ApiResponse<CustomerResponseDto>> createCustomer(
            @Valid @RequestBody CustomerRequestDto requestDto){

        CustomerResponseDto response = customerService.createCustomer(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                "Customer created successfully.",
                                HttpStatus.CREATED.value(),
                                response
                        )
                );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<CustomerResponseDto>>> getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy ,
            @RequestParam(defaultValue = "asc") String direction
    ){
        if (page < 0) {
            throw new IllegalArgumentException("Page number cannot be negative.");
        }

        if (size <= 0) {
            throw new IllegalArgumentException("Page size must be greater than zero.");
        }

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<CustomerResponseDto> customers =
                customerService.getAllCustomers(pageable);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Customers fetched successfully.",
                        HttpStatus.OK.value(),
                        customers
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponseDto>> getCustomerById(
            @Valid @PathVariable Long id
    ){
        CustomerResponseDto customer = customerService.getCustomerById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Customer fetched successfully.",
                        HttpStatus.OK.value(),
                        customer
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponseDto>> updateCustomer(
            @Valid @PathVariable Long id,
            @RequestBody CustomerRequestDto requestDto
    ){

        CustomerResponseDto response = customerService.updateCustomer(id,requestDto);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Customer updated sucessfully.",
                        HttpStatus.OK.value(),
                        response
                )
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(
            @PathVariable Long id
    ){
        customerService.deleteCustomer(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Customer deleted sucessfully",
                        HttpStatus.OK.value(),
                        null
                )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<CustomerResponseDto>>> searchCustomers(
            @RequestParam String keyword,

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ){

        Sort sort = direction.equalsIgnoreCase("desc")
                ?Sort.by(sortBy).descending()
                :Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page,size,sort);

        Page<CustomerResponseDto> customers = customerService.searchCustomers(keyword,pageable);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Customers fetched successfully.",
                        HttpStatus.OK.value(),
                        customers
                )
        );
    }
}
