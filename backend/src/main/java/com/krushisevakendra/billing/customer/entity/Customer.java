package com.krushisevakendra.billing.customer.entity;

import com.krushisevakendra.billing.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name ="customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "mobile_number", nullable = false, unique = true, length = 10)
    private String mobileNumber;

    @Column(length = 100)
    private String email;

    @Column(length = 500)
    private String address;
}
