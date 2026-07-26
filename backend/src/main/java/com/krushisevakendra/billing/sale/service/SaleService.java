package com.krushisevakendra.billing.sale.service;

import com.krushisevakendra.billing.sale.dto.SaleRequestDto;
import com.krushisevakendra.billing.sale.dto.SaleResponseDto;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface SaleService {

    public SaleResponseDto createSale(SaleRequestDto request);

    SaleResponseDto getSaleById(Long saleId);

    Page<SaleResponseDto> getAllSales(
            int page,
            int size,
            String sortBy,
            String sortDir
    );

    Page<SaleResponseDto> searchSales(
            String keyword,
            int page,
            int size,
            String sortBy,
            String sortDir
    );

    Page<SaleResponseDto> filterSalesByDate(
            LocalDate fromDate,
            LocalDate toDate,
            int page,
            int size,
            String sortBy,
            String sortDir
    );

    void deleteSale(Long saleId);
}
