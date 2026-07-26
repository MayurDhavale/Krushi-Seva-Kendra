package com.krushisevakendra.billing.sale.entity;

import com.krushisevakendra.billing.product.entity.Product;
import com.krushisevakendra.billing.product.enums.Unit;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "sale_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Parent Sale
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    // Sold Product
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // Quantity Sold
    @Column(nullable = false)
    private Integer quantity;

    // Product Unit
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Unit unit;

    // Selling Price at the time of sale
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal sellingPrice;

    // GST Percentage
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal gstRate;

    // GST Amount
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal taxAmount;

    // Total Amount (Including GST)
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalPrice;
}