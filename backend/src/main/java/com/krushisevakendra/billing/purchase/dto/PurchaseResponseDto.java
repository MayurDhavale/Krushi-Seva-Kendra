package com.krushisevakendra.billing.purchase.dto;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseResponseDto {

    private Long id;

    private String purchaseNumber;

    private Long supplierId;

    private String supplierName;

    private LocalDate purchaseDate;

    private BigDecimal subtotal;

    private BigDecimal discount;

    private BigDecimal totalTaxAmount;

    private BigDecimal totalAmount;

    private String remarks;

    private List<PurchaseItemResponseDto> purchaseItems;
}