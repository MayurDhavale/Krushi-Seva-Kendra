package com.krushisevakendra.billing.customer.repository;

import com.krushisevakendra.billing.customer.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByMobileNumber(String mobileNumber);

    Optional<Customer> findByIdAndActiveTrue(Long id);

    Page<Customer> findByActiveTrue(Pageable pageable);

    Optional<Customer> findByMobileNumber(String mobileNumber);

    Page<Customer>
    findByActiveTrueAndNameContainingIgnoreCaseOrActiveTrueAndMobileNumberContainingOrActiveTrueAndEmailContainingIgnoreCase(
            String name,
            String mobileNumber,
            String email,
            Pageable pageable
    );

    long countByActiveTrue();



}
