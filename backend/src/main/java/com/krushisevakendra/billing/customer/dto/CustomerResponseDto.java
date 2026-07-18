package com.krushisevakendra.billing.customer.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponseDto {

    private Long id;

    private String name;

    private String mobileNumber;

    private String email;

    private String address;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
