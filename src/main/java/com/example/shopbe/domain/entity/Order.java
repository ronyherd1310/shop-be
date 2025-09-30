package com.example.shopbe.domain.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public final Long id;
    @Column(name = "customer_name", nullable = false)
    public final String customerName;
    @Column(name = "customer_email", nullable = false)
    public final String customerEmail;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    public final List<OrderItem> items;
    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    public final BigDecimal totalAmount;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public final OrderStatus status;
    @Column(name = "created_at", nullable = false)
    public final LocalDateTime createdAt;
    @Column(name = "transaction_date", nullable = false)
    public final LocalDateTime transactionDate;

    public Order() {
        this.id = null;
        this.customerName = null;
        this.customerEmail = null;
        this.items = null;
        this.totalAmount = null;
        this.status = null;
        this.createdAt = null;
        this.transactionDate = null;
    }

    public Order(Long id, String customerName, String customerEmail,
                 List<OrderItem> items, BigDecimal totalAmount,
                 OrderStatus status, LocalDateTime createdAt, LocalDateTime transactionDate) {
        this.id = id;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.items = items;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
        this.transactionDate = transactionDate;
    }

    public Order(String customerName, String customerEmail,
                 List<OrderItem> items, BigDecimal totalAmount,
                 OrderStatus status, LocalDateTime createdAt, LocalDateTime transactionDate) {
        this.id = null;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.items = items;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
        this.transactionDate = transactionDate;
    }
}