package com.krushisevakendra.billing.purchase.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseRequestDto {

    @NotNull(message = "Supplier ID is required.")
    private Long supplierId;

    @NotNull(message = "Purchase date is required.")
    private LocalDate purchaseDate;

    @DecimalMin(value = "0.00")
    @Builder.Default
    private BigDecimal discount = BigDecimal.ZERO;

    @DecimalMin(value = "0.00")
    @Builder.Default
    private BigDecimal totalTaxAmount = BigDecimal.ZERO;

    private String remarks;

    @Valid
    @NotEmpty(message = "At least one purchase item is required.")
    private List<PurchaseItemRequestDto> purchaseItems;
}