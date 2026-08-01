package com.krushisevakendra.billing.supplier.repository;

import com.krushisevakendra.billing.supplier.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    boolean existsBySupplierName(String supplierName);

    boolean existsByMobileNumber(String mobileNumber);

    Optional<Supplier> findByIdAndActiveTrue(Long id);

    Optional<Supplier> findByMobileNumber(String mobileNumber);

    Page<Supplier> findByActiveTrue(Pageable pageable);

    Optional<Supplier> findBySupplierName(String supplierName);


    Optional<Supplier> findByGstNumber(String gstNumber);

    Page<Supplier> findByActiveTrueAndSupplierNameContainingIgnoreCaseOrActiveTrueAndContactPersonContainingIgnoreCaseOrActiveTrueAndMobileNumberContaining(
            String supplierName,
            String contactPerson,
            String mobileNumber,
            Pageable pageable
    );

    long countByActiveTrue();

}