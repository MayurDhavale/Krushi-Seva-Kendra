package com.krushisevakendra.billing.dashboard.services;

import com.krushisevakendra.billing.customer.repository.CustomerRepository;
import com.krushisevakendra.billing.dashboard.dto.*;
import com.krushisevakendra.billing.product.repository.ProductRepository;
import com.krushisevakendra.billing.purchase.repository.PurchaseRepository;
import com.krushisevakendra.billing.sale.repository.SaleRepository;
import com.krushisevakendra.billing.supplier.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

@RequiredArgsConstructor
@Service
public class DashboardServiceImpl implements DashboardService {

    private final SaleRepository saleRepository;
    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final SupplierRepository supplierRepository;


    @Override
    @Transactional(readOnly = true)
    public DashboardSummaryDto getDashboardSummary() {

        LocalDate today = LocalDate.now();

        return DashboardSummaryDto.builder()
                .totalProducts(productRepository.countByActiveTrue())
                .totalCustomers(customerRepository.countByActiveTrue())
                .totalSuppliers(supplierRepository.countByActiveTrue())
                .todaySales(saleRepository.getTodaySales(today))
                .todayPurchases(purchaseRepository.getTodayPurchases(today))
                .lowStockProducts(
                        productRepository.countByActiveTrueAndStockQuantityLessThanEqual(10)
                )
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecentSaleDto> getRecentSales() {

        return saleRepository
                .findTop5ByActiveTrueOrderByCreatedAtDesc()
                .stream()
                .map(sale -> RecentSaleDto.builder()
                        .invoiceNumber(sale.getInvoiceNumber())
                        .customerName(sale.getCustomer().getName())
                        .saleDate(sale.getSaleDate())
                        .totalAmount(sale.getTotalAmount())
                        .build())
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecentPurchaseDto> getRecentPurchases() {

        return purchaseRepository
                .findTop5ByActiveTrueOrderByCreatedAtDesc()
                .stream()
                .map(purchase -> RecentPurchaseDto.builder()
                        .purchaseNumber(purchase.getPurchaseNumber())
                        .supplierName(purchase.getSupplier().getSupplierName())
                        .purchaseDate(purchase.getPurchaseDate())
                        .totalAmount(purchase.getTotalAmount())
                        .build())
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LowStockProductDto> getLowStockProducts() {

        return productRepository.findByActiveTrueAndStockQuantityLessThanEqualOrderByStockQuantityAsc(10)
                .stream()
                .map(product -> LowStockProductDto.builder()
                        .productId(product.getId())
                        .productName(product.getName())
                        .categoryName(product.getCategory().getName())
                        .stockQuantity(product.getStockQuantity())
                        .unit(product.getUnit().name())
                        .build())
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MonthlySalesDto> getMonthlySale(){

        int currentYear = LocalDate.now().getYear();

        List<Object[]> results = saleRepository.getMonthlySales(currentYear);

        // Convert query result into a Map<Month, TotalSales>
        Map<Integer, BigDecimal> salesMap = new HashMap<>();

        for (Object[] row : results) {
            Integer month = ((Number) row[0]).intValue();
            BigDecimal total = (BigDecimal) row[1];
            salesMap.put(month, total);
        }

        // Always return all 12 months
        List<MonthlySalesDto> monthlySales = new ArrayList<>();

        for (int month = 1; month <= 12; month++) {

            monthlySales.add(
                    MonthlySalesDto.builder()
                            .year(currentYear)
                            .month(month)
                            .monthName(
                                    Month.of(month)
                                            .getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
                            )
                            .totalSales(
                                    salesMap.getOrDefault(month, BigDecimal.ZERO)
                            )
                            .build()
            );
        }

        return monthlySales;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MonthlyPurchaseDto> getMonthlyPurchases() {

        int currentYear = LocalDate.now().getYear();

        List<Object[]> results =
                purchaseRepository.getMonthlyPurchases(currentYear);

        Map<Integer, BigDecimal> purchaseMap = new HashMap<>();

        for (Object[] row : results) {

            Integer month = ((Number) row[0]).intValue();

            BigDecimal total = (BigDecimal) row[1];

            purchaseMap.put(month, total);
        }

        List<MonthlyPurchaseDto> monthlyPurchases = new ArrayList<>();

        for (int month = 1; month <= 12; month++) {

            monthlyPurchases.add(
                    MonthlyPurchaseDto.builder()
                            .year(currentYear)
                            .month(month)
                            .monthName(
                                    Month.of(month)
                                            .getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
                            )
                            .totalPurchases(
                                    purchaseMap.getOrDefault(month, BigDecimal.ZERO)
                            )
                            .build()
            );
        }

        return monthlyPurchases;
    }
}
