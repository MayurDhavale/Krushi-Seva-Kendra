package com.krushisevakendra.billing.supplier.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SupplierRequestDto {

    @NotBlank(message = "Supplier name is required.")
    @Size(max = 100, message = "Supplier name must not exceed 100 characters.")
    private String supplierName;

    @NotBlank(message = "Contact person is required.")
    @Size(max = 100, message = "Contact person must not exceed 100 characters.")
    private String contactPerson;

    @NotBlank(message = "Mobile number is required.")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be exactly 10 digits.")
    private String mobileNumber;

    @Email(message = "Invalid email format.")
    @Size(max = 100, message = "Email must not exceed 100 characters.")
    private String email;

    @NotBlank(message = "Address is required.")
    @Size(max = 255, message = "Address must not exceed 255 characters.")
    private String address;

    @Size(max = 15, message = "GST number must not exceed 15 characters.")
    private String gstNumber;
}