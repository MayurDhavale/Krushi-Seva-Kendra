package com.krushisevakendra.billing.sale.mapper;

import com.krushisevakendra.billing.sale.dto.SaleItemResponseDto;
import com.krushisevakendra.billing.sale.dto.SaleResponseDto;
import com.krushisevakendra.billing.sale.entity.Sale;
import com.krushisevakendra.billing.sale.entity.SaleItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SaleMapper {

    /**
     * Convert Sale Entity -> SaleResponseDto
     */
    public SaleResponseDto toResponse(Sale sale) {

        List<SaleItemResponseDto> saleItems = sale.getSaleItems()
                .stream()
                .map(this::toSaleItemResponse)
                .toList();

        return SaleResponseDto.builder()
                .id(sale.getId())
                .invoiceNumber(sale.getInvoiceNumber())
                .customerId(sale.getCustomer().getId())
                .customerName(sale.getCustomer().getName())
                .saleDate(sale.getSaleDate())
                .subtotal(sale.getSubtotal())
                .totalTaxAmount(sale.getTotalTaxAmount())
                .discount(sale.getDiscount())
                .totalAmount(sale.getTotalAmount())
                .remarks(sale.getRemarks())
                .saleItems(saleItems)
                .build();
    }

    /**
     * Convert SaleItem Entity -> SaleItemResponseDto
     */
    private SaleItemResponseDto toSaleItemResponse(SaleItem saleItem) {

        return SaleItemResponseDto.builder()
                .productId(saleItem.getProduct().getId())
                .productName(saleItem.getProduct().getName())
                .quantity(saleItem.getQuantity())
                .unit(saleItem.getUnit())
                .sellingPrice(saleItem.getSellingPrice())
                .gstRate(saleItem.getGstRate())
                .taxAmount(saleItem.getTaxAmount())
                .totalPrice(saleItem.getTotalPrice())
                .build();
    }
}