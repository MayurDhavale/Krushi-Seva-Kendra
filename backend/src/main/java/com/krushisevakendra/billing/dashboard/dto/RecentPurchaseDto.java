package com.krushisevakendra.billing.dashboard.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecentPurchaseDto {

    private String purchaseNumber;

    private String supplierName;

    private LocalDate purchaseDate;

    private BigDecimal totalAmount;
}