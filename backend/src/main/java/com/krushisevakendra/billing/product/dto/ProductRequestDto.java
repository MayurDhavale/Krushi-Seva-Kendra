package com.krushisevakendra.billing.product.dto;

import com.krushisevakendra.billing.product.enums.Unit;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequestDto {

    @NotBlank(message = "Product name is required.")
    @Size(max = 100, message = "Product name must not exceed 100 characters.")
    private String name;

    @Size(max = 500, message = "Description must not exceed 500 characters.")
    private String description;

    @NotNull(message = "Purchase price is required.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Purchase price must be greater than zero.")
    private BigDecimal purchasePrice;

    @NotNull(message = "Selling price is required.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Selling price must be greater than zero.")
    private BigDecimal sellingPrice;

    @NotNull(message = "GST rate is required.")
    @DecimalMin(value = "0.00", message = "GST rate cannot be negative.")
    @DecimalMax(value = "100.00", message = "GST rate cannot exceed 100%.")
    private BigDecimal gstRate;

    @NotNull(message = "Stock quantity is required.")
    @Min(value = 0, message = "Stock cannot be negative.")
    private Integer stockQuantity;

    @NotNull(message = "Unit is required.")
    private Unit unit;

    @NotNull(message = "Category Id is required.")
    private Long categoryId;
}
