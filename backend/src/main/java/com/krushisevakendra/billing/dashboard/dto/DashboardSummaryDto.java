package com.krushisevakendra.billing.dashboard.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardSummaryDto {

    private Long totalProducts;

    private Long totalCustomers;

    private Long totalSuppliers;

    private BigDecimal todaySales;

    private BigDecimal todayPurchases;

    private Long lowStockProducts;
}