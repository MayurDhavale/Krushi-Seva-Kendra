package com.krushisevakendra.billing.dashboard.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LowStockProductDto {

    private Long productId;

    private String productName;

    private String categoryName;

    private Integer stockQuantity;

    private String unit;
}
