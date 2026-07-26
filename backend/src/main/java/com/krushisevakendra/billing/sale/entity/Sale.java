package com.krushisevakendra.billing.sale.entity;

import com.krushisevakendra.billing.common.entity.BaseEntity;
import com.krushisevakendra.billing.customer.entity.Customer;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sales")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sale extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Auto-generated invoice number (INV-000001)
    @Column(nullable = false, unique = true, length = 20)
    private String invoiceNumber;

    // Customer who purchased the products
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // Sale date
    @Column(nullable = false)
    private LocalDate saleDate;

    // Total before GST and discount
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;

    // Total GST
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalTaxAmount;

    // Overall discount
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal discount;

    // Final payable amount
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    // Optional remarks
    @Column(length = 500)
    private String remarks;

    // Soft delete
    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    // One sale can have multiple sale items
    @OneToMany(
            mappedBy = "sale",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<SaleItem> saleItems = new ArrayList<>();
}