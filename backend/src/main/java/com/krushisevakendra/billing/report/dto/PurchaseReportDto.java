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
public class PurchaseReportDto {

    private LocalDate fromDate;

    private LocalDate toDate;

    private Long totalPurchases;

    private BigDecimal totalAmount;

    private List<PurchaseReportItemDto> purchases;
}
