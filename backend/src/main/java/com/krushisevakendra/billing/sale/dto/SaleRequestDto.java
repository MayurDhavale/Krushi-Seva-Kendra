package com.krushisevakendra.billing.sale.dto;
import jakarta.validation.Valid;
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
public class SaleRequestDto {

    @NotNull(message = "Customer ID is required.")
    private Long customerId;

    @NotNull(message = "Sale date is required.")
    private LocalDate saleDate;

    @Builder.Default
    private BigDecimal discount = BigDecimal.ZERO;

    private String remarks;

    @Valid
    @NotEmpty(message = "At least one product is required.")
    private List<SaleItemRequestDto> saleItems;
}