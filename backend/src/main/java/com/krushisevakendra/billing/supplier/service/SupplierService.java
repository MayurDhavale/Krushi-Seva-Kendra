package com.krushisevakendra.billing.supplier.service;


import com.krushisevakendra.billing.supplier.dto.SupplierRequestDto;
import com.krushisevakendra.billing.supplier.dto.SupplierResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SupplierService {

    SupplierResponseDto createSupplier(SupplierRequestDto requestDto);

    Page<SupplierResponseDto> getAllSuppliers(Pageable pageable);

    SupplierResponseDto getSupplierById(Long id);

    SupplierResponseDto updateSupplier(Long id, SupplierRequestDto requestDto);

    void deleteSupplier(Long id);

    Page<SupplierResponseDto> searchSuppliers(String keyword, Pageable pageable);


}