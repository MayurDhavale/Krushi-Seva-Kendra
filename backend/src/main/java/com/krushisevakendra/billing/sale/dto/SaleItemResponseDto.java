package com.krushisevakendra.billing.sale.dto;


import com.krushisevakendra.billing.product.enums.Unit;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleItemResponseDto {

    private Long productId;

    private String productName;

    private Integer quantity;

    private Unit unit;

    private BigDecimal sellingPrice;

    private BigDecimal gstRate;

    private BigDecimal taxAmount;

    private BigDecimal totalPrice;
}