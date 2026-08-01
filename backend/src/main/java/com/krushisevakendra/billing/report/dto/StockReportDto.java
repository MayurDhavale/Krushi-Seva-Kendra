package com.krushisevakendra.billing.report.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockReportDto {

    private Long totalProducts;

    private Long lowStockProducts;

    private Long outOfStockProducts;

    private List<StockReportItemDto> products;
}
