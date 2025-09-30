package com.example.shopbe.domain.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public final Long id;
    @Column(name = "product_id", nullable = false)
    public final Long productId;
    @Column(name = "product_name", nullable = false)
    public final String productName;
    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    public final BigDecimal unitPrice;
    @Column(nullable = false)
    public final Integer quantity;
    @Column(nullable = false, precision = 10, scale = 2)
    public final BigDecimal subtotal;

    public OrderItem() {
        this.id = null;
        this.productId = null;
        this.productName = null;
        this.unitPrice = null;
        this.quantity = null;
        this.subtotal = null;
    }

    public OrderItem(Long id, Long productId, String productName, BigDecimal unitPrice, Integer quantity) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.subtotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public OrderItem(Long productId, String productName, BigDecimal unitPrice, Integer quantity) {
        this.id = null;
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.subtotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}