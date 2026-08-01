package com.krushisevakendra.billing.report.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalesReportDto {

    private LocalDate fromDate;

    private LocalDate toDate;

    private Long totalInvoices;

    private BigDecimal totalSales;

    private List<SalesReportItemDto> sales;
}
