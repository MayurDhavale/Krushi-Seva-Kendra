package com.krushisevakendra.billing.sale.repository;

import com.krushisevakendra.billing.sale.entity.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public interface SaleItemRepository extends JpaRepository<SaleItem,Long> {

    @Query("""
SELECT COALESCE(
SUM(
si.quantity * si.product.purchasePrice
),0)
FROM SaleItem si
WHERE si.sale.active=true
AND si.sale.saleDate BETWEEN :fromDate AND :toDate
""")
    BigDecimal getTotalCost(
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate
    );
}
