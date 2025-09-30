package com.example.shopbe.application.service;

import com.example.shopbe.domain.entity.Order;
import com.example.shopbe.domain.entity.OrderItem;
import com.example.shopbe.domain.entity.OrderStatus;
import com.example.shopbe.domain.repository.OrderRepository;
import com.example.shopbe.domain.usecase.CreateOrderUseCase;
import com.example.shopbe.domain.usecase.GetOrderUseCase;
import com.example.shopbe.domain.usecase.ListOrdersUseCase;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService implements CreateOrderUseCase, GetOrderUseCase, ListOrdersUseCase {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(String customerName, String customerEmail, List<OrderItem> items) {
        List<Order> existingOrders = orderRepository.findByCustomerEmail(customerEmail);
        return existingOrders.isEmpty()
            ? createNewOrder(customerName, customerEmail, items)
            : updateExistingOrder(existingOrders.get(0), customerName, customerEmail, items);
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> getOrdersByCustomerEmail(String customerEmail) {
        return orderRepository.findByCustomerEmail(customerEmail);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> items) {
        return items.stream()
                .map(item -> item.subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Map<Long, Integer> convertItemsToProductQuantityMap(List<OrderItem> items) {
        return items.stream()
                .collect(Collectors.toMap(
                        item -> item.productId,
                        item -> item.quantity,
                        Integer::sum
                ));
    }

    private Order createNewOrder(String customerName, String customerEmail, List<OrderItem> items) {
        BigDecimal totalAmount = calculateTotalAmount(items);
        Order order = new Order(
                customerName,
                customerEmail,
                items,
                totalAmount,
                OrderStatus.PENDING,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        return orderRepository.save(order);
    }

    private Order updateExistingOrder(Order existingOrder, String customerName, String customerEmail, List<OrderItem> items) {
        Map<Long, Integer> newQuantities = convertItemsToProductQuantityMap(items);
        Map<Long, OrderItem> existingItemsMap = existingOrder.items.stream()
                .collect(Collectors.toMap(item -> item.productId, item -> item));

        List<OrderItem> updatedItems = items.stream()
                .map(newItem -> existingItemsMap.containsKey(newItem.productId)
                        ? new OrderItem(
                                existingItemsMap.get(newItem.productId).id,
                                newItem.productId,
                                newItem.productName,
                                newItem.unitPrice,
                                newQuantities.get(newItem.productId))
                        : new OrderItem(
                                newItem.productId,
                                newItem.productName,
                                newItem.unitPrice,
                                newQuantities.get(newItem.productId)))
                .collect(Collectors.toList());

        Order updatedOrder = new Order(
                existingOrder.id,
                customerName,
                customerEmail,
                updatedItems,
                calculateTotalAmount(updatedItems),
                existingOrder.status,
                existingOrder.createdAt,
                existingOrder.transactionDate
        );

        return orderRepository.save(updatedOrder);
    }
}