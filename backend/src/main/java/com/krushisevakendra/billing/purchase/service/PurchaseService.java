package com.krushisevakendra.billing.purchase.service;

import com.krushisevakendra.billing.purchase.dto.PurchaseRequestDto;
import com.krushisevakendra.billing.purchase.dto.PurchaseResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface PurchaseService {

    PurchaseResponseDto createPurchase(PurchaseRequestDto request);

    PurchaseResponseDto getPurchaseById(Long id);

    Page<PurchaseResponseDto> getAllPurchase(Pageable pageable);

    Page<PurchaseResponseDto> searchPurchases(String keyword, Pageable pageable);

    Page<PurchaseResponseDto> filterPurchaseByDate(
            LocalDate fromDate,
            LocalDate toDate,
            Pageable pageable
    );

    void deletePurchase(Long id);


}
