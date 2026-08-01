package com.krushisevakendra.billing.dashboard.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyPurchaseDto {

    private Integer year;

    private Integer month;

    private String monthName;

    private BigDecimal totalPurchases;
}