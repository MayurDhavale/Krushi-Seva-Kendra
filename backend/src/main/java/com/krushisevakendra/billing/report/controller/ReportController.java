package com.krushisevakendra.billing.report.controller;

import com.krushisevakendra.billing.common.response.ApiResponse;
import com.krushisevakendra.billing.report.dto.ProfitReportDto;
import com.krushisevakendra.billing.report.dto.PurchaseReportDto;
import com.krushisevakendra.billing.report.dto.SalesReportDto;
import com.krushisevakendra.billing.report.dto.StockReportDto;
import com.krushisevakendra.billing.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/sales")
    public ResponseEntity<ApiResponse<SalesReportDto>> getSaleReort(

            @RequestParam LocalDate fromDate,
            @RequestParam LocalDate toDate
            ){
        SalesReportDto response = reportService.getSaleReport(fromDate,toDate);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Sales report retrieved successfully.",
                        HttpStatus.OK.value(),
                        response
                )
        );
    }

    @GetMapping("/purchases")
    public ResponseEntity<ApiResponse<PurchaseReportDto>> getPurchaseReport(

            @RequestParam LocalDate fromDate,

            @RequestParam LocalDate toDate
    ) {

        PurchaseReportDto response =
                reportService.getPurchaseReport(fromDate, toDate);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Purchase report retrieved successfully.",
                        response
                )
        );
    }

    @GetMapping("/stock")
    public ResponseEntity<ApiResponse<StockReportDto>> getStockReport(){

        StockReportDto response = reportService.getStockReport();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Stock report retrieved successfully.",
                        HttpStatus.OK.value(),
                        response
                )
        );
    }

    @GetMapping("/profit")
    public ResponseEntity<ApiResponse<ProfitReportDto>> getProfitReport(

            @RequestParam LocalDate fromDate,
            @RequestParam LocalDate toDate
    ){

        ProfitReportDto response = reportService.getProfitReport(fromDate,toDate);

        return ResponseEntity.ok(
                ApiResponse.success(

                        "Profit report retrieved successfully.",
                        HttpStatus.OK.value(),
                        response
                )
        );
    }
}
