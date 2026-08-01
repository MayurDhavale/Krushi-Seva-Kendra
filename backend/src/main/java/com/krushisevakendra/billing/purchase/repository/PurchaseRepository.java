package com.krushisevakendra.billing.purchase.repository;

import com.krushisevakendra.billing.purchase.entity.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<Purchase,Long> {

    Optional<Purchase> findByPurchaseNumber(String purchaseNumber);

    Optional<Purchase> findByIdAndActiveTrue(Long id);

    boolean existsByPurchaseNumber(String purchaseNumber);

    Optional<Purchase> findTopByOrderByIdDesc();

    Page<Purchase> findAllByActiveTrue(Pageable pageable);

    Page<Purchase> findByActiveTrueAndPurchaseNumberContainingIgnoreCaseOrActiveTrueAndSupplier_SupplierNameContainingIgnoreCase(
            String purchaseNumber,
            String supplierName,
            Pageable pageable
    );
    
    @Query("""
            Select p
            From Purchase p
            Where p.active = true
            AND p.purchaseDate BETWEEN :fromDate AND :toDate
            """)
    Page<Purchase> filterByPurchaseDateBetween(
            @Param("fromDate")LocalDate fromDate,
            @Param("toDate")LocalDate toDate,
            Pageable pageable
            );

    @Query("""
        SELECT COALESCE(SUM(p.totalAmount), 0)
        FROM Purchase p
        WHERE p.active = true
        AND p.purchaseDate = :today
        """)
    BigDecimal getTodayPurchases(@Param("today") LocalDate today);

    List<Purchase> findTop5ByActiveTrueOrderByCreatedAtDesc();

    @Query("""
       SELECT MONTH(p.purchaseDate),
              COALESCE(SUM(p.totalAmount),0)
       FROM Purchase p
       WHERE p.active = true
       AND YEAR(p.purchaseDate)=:year
       GROUP BY MONTH(p.purchaseDate)
       ORDER BY MONTH(p.purchaseDate)
       """)
    List<Object[]> getMonthlyPurchases(@Param("year") int year);

    List<Purchase> findByActiveTrueAndPurchaseDateBetweenOrderByPurchaseDateAsc(
            LocalDate fromDate,
            LocalDate toDate
    );

    @Query("""
       SELECT COALESCE(SUM(p.totalAmount),0)
       FROM Purchase p
       WHERE p.active = true
       AND p.purchaseDate BETWEEN :fromDate AND :toDate
       """)
    BigDecimal getTotalPurchaseAmountBetween(
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate
    );
}
