package com.krushisevakendra.billing.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequestDto {

    @NotBlank(message = "Customer name is required.")
    @Size(max = 100, message = "Customer name cannot exceed 100 characters.")
    private String name;

    @NotBlank(message = "Mobile number is required.")
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Mobile number must be a valid 10-digit Indian mobile number."
    )
    private String mobileNumber;

    @Email(message = "Invalid email format.")
    @Size(max = 100, message = "Email cannot exceed 100 characters.")
    private String email;

    @Size(max = 500, message = "Address cannot exceed 500 characters.")
    private String address;
}
