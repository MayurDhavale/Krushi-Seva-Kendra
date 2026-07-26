package com.krushisevakendra.billing.sale.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleItemRequestDto {

    @NotNull(message = "Product ID is required.")
    private Long productId;

    @NotNull(message = "Quantity is required.")
    @Min(value = 1, message = "Quantity must be greater than zero.")
    private Integer quantity;
}
