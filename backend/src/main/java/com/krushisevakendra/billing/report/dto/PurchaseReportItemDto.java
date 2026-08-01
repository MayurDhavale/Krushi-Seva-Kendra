package com.krushisevakendra.billing.report.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseReportItemDto {

    private String purchaseNumber;

    private String supplierName;

    private LocalDate purchaseDate;

    private BigDecimal totalAmount;
}
