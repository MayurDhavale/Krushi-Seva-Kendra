package com.krushisevakendra.billing.supplier.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SupplierResponseDto {

    private Long id;

    private String supplierName;

    private String contactPerson;

    private String mobileNumber;

    private String email;

    private String address;

    private String gstNumber;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}