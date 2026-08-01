package com.krushisevakendra.billing.dashboard.controller;

import com.krushisevakendra.billing.common.response.ApiResponse;
import com.krushisevakendra.billing.dashboard.dto.*;
import com.krushisevakendra.billing.dashboard.services.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<DashboardSummaryDto>> getDashboardSummary() {

        DashboardSummaryDto response = dashboardService.getDashboardSummary();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Dashboard summary retrieved successfully.",
                        HttpStatus.OK.value(),
                        response
                )
        );
    }

    @GetMapping("/recent-sales")
    public ResponseEntity<ApiResponse<List<RecentSaleDto>>> getRecentSales() {

        List<RecentSaleDto> response =
                dashboardService.getRecentSales();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Recent sales retrieved successfully.",
                        HttpStatus.OK.value(),
                        response
                )
        );
    }


    @GetMapping("/recent-purchases")
    public ResponseEntity<ApiResponse<List<RecentPurchaseDto>>> getRecentPurchases() {

        List<RecentPurchaseDto> response =
                dashboardService.getRecentPurchases();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Recent purchases retrieved successfully.",
                        HttpStatus.OK.value(),
                        response
                )
        );
    }

    @GetMapping("/low-stock")
    public ResponseEntity<ApiResponse<List<LowStockProductDto>>> getLowStockProducts(){

        List<LowStockProductDto> response = dashboardService.getLowStockProducts();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Low stock products retrieved successfully.",
                        HttpStatus.OK.value(),
                        response
                )
        );
    }


    @GetMapping("/monthly-sales")
    public ResponseEntity<ApiResponse<List<MonthlySalesDto>>> getMonthlySales(){

        List<MonthlySalesDto> response = dashboardService.getMonthlySale();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Monthly sales retrieved successfully.",
                        HttpStatus.OK.value(),
                        response
                )
        );
    }

    @GetMapping("/monthly-purchases")
    public ResponseEntity<ApiResponse<List<MonthlyPurchaseDto>>> getMonthlyPurchases() {

        List<MonthlyPurchaseDto> response =
                dashboardService.getMonthlyPurchases();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Monthly purchases retrieved successfully.",
                        response
                )
        );
    }
}