package com.krushisevakendra.billing.dashboard.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonthlySalesDto {

    private Integer year;

    private Integer month;

    private String monthName;

    private BigDecimal totalSales;
}
