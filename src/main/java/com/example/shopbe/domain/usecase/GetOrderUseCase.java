package com.example.shopbe.domain.usecase;

import com.example.shopbe.domain.entity.Order;

import java.util.List;
import java.util.Optional;

public interface GetOrderUseCase {
    Optional<Order> getOrderById(Long id);
    List<Order> getOrdersByCustomerEmail(String customerEmail);
}