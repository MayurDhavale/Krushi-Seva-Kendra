package com.krushisevakendra.billing.purchase.mapper;

import com.krushisevakendra.billing.purchase.dto.PurchaseItemResponseDto;
import com.krushisevakendra.billing.purchase.dto.PurchaseResponseDto;
import com.krushisevakendra.billing.purchase.entity.Purchase;
import com.krushisevakendra.billing.purchase.entity.PurchaseItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PurchaseMapper {

    public PurchaseResponseDto toResponse(Purchase purchase) {

        List<PurchaseItemResponseDto> itemDtos =
                purchase.getPurchaseItems()
                        .stream()
                        .map(this::toPurchaseItemResponse)
                        .toList();

        return PurchaseResponseDto.builder()
                .id(purchase.getId())
                .purchaseNumber(purchase.getPurchaseNumber())
                .supplierId(purchase.getSupplier().getId())
                .supplierName(purchase.getSupplier().getSupplierName())
                .purchaseDate(purchase.getPurchaseDate())
                .subtotal(purchase.getSubtotal())
                .discount(purchase.getDiscount())
                .totalTaxAmount(purchase.getTotalTaxAmount())
                .totalAmount(purchase.getTotalAmount())
                .remarks(purchase.getRemarks())
                .purchaseItems(itemDtos)
                .build();
    }

    private PurchaseItemResponseDto toPurchaseItemResponse(PurchaseItem item) {

        return PurchaseItemResponseDto.builder()
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getName())
                .quantity(item.getQuantity())
                .unit(item.getUnit())
                .purchasePrice(item.getPurchasePrice())
                .gstRate(item.getGstRate())
                .taxAmount(item.getTaxAmount())
                .totalPrice(item.getTotalPrice())
                .build();
    }
}