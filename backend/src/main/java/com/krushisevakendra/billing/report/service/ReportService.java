package com.krushisevakendra.billing.report.service;

import com.krushisevakendra.billing.report.dto.ProfitReportDto;
import com.krushisevakendra.billing.report.dto.PurchaseReportDto;
import com.krushisevakendra.billing.report.dto.SalesReportDto;
import com.krushisevakendra.billing.report.dto.StockReportDto;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

public interface ReportService {

    SalesReportDto getSaleReport(LocalDate fromDate, LocalDate toDate);

    PurchaseReportDto getPurchaseReport(LocalDate fromDate, LocalDate toDate);

    StockReportDto getStockReport();

    ProfitReportDto getProfitReport(LocalDate fromDate, LocalDate toDate);

}
