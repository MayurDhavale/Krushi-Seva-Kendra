package com.krushisevakendra.billing.customer.mapper;

import com.krushisevakendra.billing.customer.dto.CustomerRequestDto;
import com.krushisevakendra.billing.customer.dto.CustomerResponseDto;
import com.krushisevakendra.billing.customer.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toEntity(CustomerRequestDto dto) {

        Customer customer = new Customer();

        customer.setName(dto.getName());
        customer.setMobileNumber(dto.getMobileNumber());
        customer.setEmail(dto.getEmail());
        customer.setAddress(dto.getAddress());

        return customer;
    }

    public CustomerResponseDto toResponse(Customer customer) {

        return CustomerResponseDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .mobileNumber(customer.getMobileNumber())
                .email(customer.getEmail())
                .address(customer.getAddress())
                .active(customer.getActive())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }
}
