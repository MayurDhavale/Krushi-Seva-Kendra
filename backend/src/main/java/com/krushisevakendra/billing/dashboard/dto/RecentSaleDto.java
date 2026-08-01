package com.krushisevakendra.billing.dashboard.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecentSaleDto {

    private String invoiceNumber;

    private String customerName;

    private LocalDate saleDate;

    private BigDecimal totalAmount;
}