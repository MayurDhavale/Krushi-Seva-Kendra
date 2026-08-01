package com.krushisevakendra.billing.report.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalesReportItemDto {

    private String invoiceNumber;

    private String customerName;

    private LocalDate saleDate;

    private BigDecimal totalAmount;
}
