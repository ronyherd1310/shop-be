package com.example.shopbe.domain.usecase;

import com.example.shopbe.domain.entity.Order;
import com.example.shopbe.domain.entity.OrderItem;

import java.util.List;

public interface CreateOrderUseCase {
    Order createOrder(String customerName, String customerEmail, List<OrderItem> items);
}