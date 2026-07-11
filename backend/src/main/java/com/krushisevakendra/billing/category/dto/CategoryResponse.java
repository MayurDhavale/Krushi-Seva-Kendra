package com.krushisevakendra.billing.category.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {

    private Long id;

    private String name;

    private String description;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}