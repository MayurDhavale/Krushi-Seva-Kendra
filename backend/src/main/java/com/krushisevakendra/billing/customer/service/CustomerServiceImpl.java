package com.krushisevakendra.billing.customer.service;

import com.krushisevakendra.billing.customer.dto.CustomerRequestDto;
import com.krushisevakendra.billing.customer.dto.CustomerResponseDto;
import com.krushisevakendra.billing.customer.entity.Customer;
import com.krushisevakendra.billing.customer.mapper.CustomerMapper;
import com.krushisevakendra.billing.customer.repository.CustomerRepository;
import com.krushisevakendra.billing.exception.DuplicateResourceException;
import com.krushisevakendra.billing.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;


    @Override
    @Transactional
    public CustomerResponseDto createCustomer(CustomerRequestDto requestDto) {
        //This checks whether the mobile number already exists.
        if(customerRepository.existsByMobileNumber(requestDto.getMobileNumber())){
            throw  new DuplicateResourceException( "Customer already exists with mobile number : " + requestDto.getMobileNumber());

        }

        Customer customer = customerMapper.toEntity(requestDto);

        Customer savedCustomer = customerRepository.save(customer);

        return customerMapper.toResponse(savedCustomer);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerResponseDto> getAllCustomers(Pageable pageable) {

        return customerRepository.findByActiveTrue(pageable)
                .map(customerMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponseDto getCustomerById(Long id) {

        Customer customer = customerRepository.findByIdAndActiveTrue(id)
                .orElseThrow(()-> new ResourceNotFoundException("Customer Not found with id : "+ id));

        return customerMapper.toResponse(customer);
    }

    @Override
    @Transactional
    public CustomerResponseDto updateCustomer(Long id, CustomerRequestDto requestDto) {

        // 1. Fetch Customer
        Customer customer = customerRepository.findByIdAndActiveTrue(id)
                .orElseThrow(()-> new ResourceNotFoundException("Customer not found with id : "+ id));

        // Find Customer by Mobile Number
        Optional<Customer> existingCustomer  = customerRepository.findByMobileNumber(requestDto.getMobileNumber());


        // Step 3: Duplicate Validation
        if(existingCustomer.isPresent() && !existingCustomer.get().getId().equals(id)){
            throw new DuplicateResourceException("Customer already exists with mobile number : "+
                    requestDto.getMobileNumber());
        }

        customer.setName(requestDto.getName());
        customer.setEmail(requestDto.getEmail());
        customer.setMobileNumber(requestDto.getMobileNumber());
        customer.setAddress(requestDto.getMobileNumber());

        Customer updatedCustomer = customerRepository.save(customer);

        return customerMapper.toResponse(updatedCustomer);
    }

    @Override
    @Transactional
    public void deleteCustomer(Long id) {

        Customer customer = customerRepository.findByIdAndActiveTrue(id)
                .orElseThrow(()-> new ResourceNotFoundException("Customer Not Found with id  : "+ id));

        customer
                .setActive(false);

        customerRepository.save(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerResponseDto> searchCustomers(String keyword, Pageable pageable) {
        return customerRepository.findByActiveTrueAndNameContainingIgnoreCaseOrActiveTrueAndMobileNumberContainingOrActiveTrueAndEmailContainingIgnoreCase(
                keyword,
                keyword,
                keyword,
                pageable
        ).map(customerMapper::toResponse);
    }
}
