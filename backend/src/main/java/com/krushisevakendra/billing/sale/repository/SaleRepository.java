package com.krushisevakendra.billing.sale.repository;

import com.krushisevakendra.billing.sale.entity.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale,Long> {


    Optional<Sale> findByInvoiceNumber(String invoiceNumber);

    Optional<Sale> findByIdAndActiveTrue(Long id);

    boolean existsByInvoiceNumber(String invoiceNumber);

    Optional<Sale> findTopByOrderByIdDesc();

    Page<Sale> findAllByActiveTrue(Pageable pageable);

    Page<Sale> findByActiveTrueAndInvoiceNumberContainingIgnoreCaseOrActiveTrueAndCustomer_NameContainingIgnoreCase(
            String invoiceNumber,
            String customerName,
            Pageable pageable
    );

    @Query("""
        SELECT s
        FROM Sale s
        WHERE s.active = true
        AND s.saleDate BETWEEN :fromDate AND :toDate
        """)
    Page<Sale> findBySaleDateBetween(
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate,
            Pageable pageable
    );
}
