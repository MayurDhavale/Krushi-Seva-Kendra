package com.krushisevakendra.billing.report.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockReportItemDto {

    private Long productId;

    private String productName;

    private String categoryName;

    private Integer stockQuantity;

    private String unit;

    private BigDecimal purchasePrice;

    private BigDecimal sellingPrice;

}
