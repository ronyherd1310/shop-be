package com.example.shopbe.domain.usecase;

import com.example.shopbe.domain.entity.Order;

import java.util.List;

public interface ListOrdersUseCase {
    List<Order> getAllOrders();
}