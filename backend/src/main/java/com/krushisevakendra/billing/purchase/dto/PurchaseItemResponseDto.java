package com.krushisevakendra.billing.purchase.dto;
import com.krushisevakendra.billing.product.enums.Unit;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseItemResponseDto {

    private Long productId;

    private String productName;

    private Integer quantity;

    private BigDecimal purchasePrice;

    private BigDecimal gstRate;

    private BigDecimal taxAmount;

    private BigDecimal totalPrice;

    private Unit unit;
}