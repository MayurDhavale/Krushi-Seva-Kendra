package com.krushisevakendra.billing.report.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfitReportDto {

    private LocalDate fromDate;

    private LocalDate toDate;

    private BigDecimal totalSales;

    private BigDecimal totalCost;

    private BigDecimal grossProfit;

    private BigDecimal profitPercentage;
}
