package com.krushisevakendra.billing.supplier.entity;

import com.krushisevakendra.billing.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "suppliers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Supplier extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String supplierName;

    @Column(nullable = false, length = 100)
    private String contactPerson;

    @Column(nullable = false, unique = true, length = 10)
    private String mobileNumber;

    @Column(length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(length = 15)
    private String gstNumber;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;
}
