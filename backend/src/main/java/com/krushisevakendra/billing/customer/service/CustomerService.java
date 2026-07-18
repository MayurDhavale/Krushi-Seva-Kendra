package com.krushisevakendra.billing.customer.service;

import com.krushisevakendra.billing.customer.dto.CustomerRequestDto;
import com.krushisevakendra.billing.customer.dto.CustomerResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

    CustomerResponseDto createCustomer(CustomerRequestDto requestDto);

    Page<CustomerResponseDto> getAllCustomers(Pageable pageable);

    CustomerResponseDto getCustomerById(Long id);

    CustomerResponseDto updateCustomer(Long id, CustomerRequestDto requestDto);

    void deleteCustomer(Long id);

    Page<CustomerResponseDto> searchCustomers(String keyword, Pageable pageable);
}
