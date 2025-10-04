package com.example.shopbe.domain.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public final Long id;
    @Column(nullable = false)
    public final String name;
    @Column(length = 1000)
    public final String description;
    @Column(nullable = false, precision = 10, scale = 2)
    public final BigDecimal price;
    @Column(length = 500)
    public final String url;

    public Product() {
        this.id = null;
        this.name = null;
        this.description = null;
        this.price = null;
        this.url = null;
    }

    public Product(Long id, String name, String description, BigDecimal price, String url) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.url = url;
    }
}