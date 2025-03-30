package com.yuu.customer_service.entity;

import com.yuu.customer_service.validation.ValidField;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ValidField(minLength = 3, maxLength = 100, requireLetters = true, requireNumbers = false, allowNumbers = false)
    private String name;

    @ValidField(minLength = 5, maxLength = 100, requireLetters = true, requireNumbers = true, allowNumbers = true)
    @Column(nullable = false, unique = true)
    private String email;

    @ValidField(minLength = 10, maxLength = 15, requireLetters = false, requireNumbers = true, allowNumbers = true)
    private String phone;
}
