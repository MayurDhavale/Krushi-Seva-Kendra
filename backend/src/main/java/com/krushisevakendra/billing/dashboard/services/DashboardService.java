package com.krushisevakendra.billing.dashboard.services;

import com.krushisevakendra.billing.dashboard.dto.*;

import java.util.List;

public interface DashboardService {

    DashboardSummaryDto getDashboardSummary();

    List<RecentSaleDto> getRecentSales();

    List<RecentPurchaseDto> getRecentPurchases();

    List<LowStockProductDto> getLowStockProducts();

    List<MonthlySalesDto> getMonthlySale();

    List<MonthlyPurchaseDto> getMonthlyPurchases();

}
