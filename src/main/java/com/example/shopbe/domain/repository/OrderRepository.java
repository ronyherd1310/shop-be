package com.example.shopbe.domain.repository;

import com.example.shopbe.domain.entity.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(Long id);
    List<Order> findAll();
    List<Order> findByCustomerEmail(String customerEmail);
    List<Order> findByCustomerEmailAndDate(String customerEmail, LocalDateTime transactionDate);
    void deleteById(Long id);
}