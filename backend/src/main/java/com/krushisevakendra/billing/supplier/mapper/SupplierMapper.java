package com.krushisevakendra.billing.supplier.mapper;

import com.krushisevakendra.billing.supplier.dto.SupplierRequestDto;
import com.krushisevakendra.billing.supplier.dto.SupplierResponseDto;
import com.krushisevakendra.billing.supplier.entity.Supplier;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {

    public Supplier toEntity(SupplierRequestDto dto) {

        Supplier supplier = new Supplier();

        supplier.setSupplierName(dto.getSupplierName());
        supplier.setContactPerson(dto.getContactPerson());
        supplier.setMobileNumber(dto.getMobileNumber());
        supplier.setEmail(dto.getEmail());
        supplier.setAddress(dto.getAddress());
        supplier.setGstNumber(dto.getGstNumber());

        return supplier;
    }

    public SupplierResponseDto toResponse(Supplier supplier) {

        return SupplierResponseDto.builder()
                .id(supplier.getId())
                .supplierName(supplier.getSupplierName())
                .contactPerson(supplier.getContactPerson())
                .mobileNumber(supplier.getMobileNumber())
                .email(supplier.getEmail())
                .address(supplier.getAddress())
                .gstNumber(supplier.getGstNumber())
                .active(supplier.getActive())
                .createdAt(supplier.getCreatedAt())
                .updatedAt(supplier.getUpdatedAt())
                .build();
    }
}