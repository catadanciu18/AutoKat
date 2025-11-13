package com.example.siteauto.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter @Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String brand;

    private String category;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    private Integer stock;

    private boolean active = true;
}
