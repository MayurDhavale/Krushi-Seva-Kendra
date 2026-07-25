package com.krushisevakendra.billing.product.dto;

import com.krushisevakendra.billing.product.enums.Unit;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDto {

    private Long id;

    private String name;

    private String description;

    private BigDecimal purchasePrice;

    private BigDecimal sellingPrice;

    private BigDecimal gstRate;

    private Integer stockQuantity;

    private Unit unit;

    private Boolean active;

    private Long categoryId;

    private String categoryName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
