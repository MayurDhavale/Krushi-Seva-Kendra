package com.krushisevakendra.billing.sale.dto;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleResponseDto {

    private Long id;

    private String invoiceNumber;

    private Long customerId;

    private String customerName;

    private LocalDate saleDate;

    private BigDecimal subtotal;

    private BigDecimal totalTaxAmount;

    private BigDecimal discount;

    private BigDecimal totalAmount;

    private String remarks;

    private List<SaleItemResponseDto> saleItems;
}