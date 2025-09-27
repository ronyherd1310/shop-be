package com.example.shopbe.infrastructure.repository;

import com.example.shopbe.domain.entity.Order;
import com.example.shopbe.domain.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class InMemoryOrderRepository implements OrderRepository {

    private final Map<Long, Order> orders = new ConcurrentHashMap<>();

    @Override
    public Order save(Order order) {
        orders.put(order.getId(), order);
        return order;
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(orders.get(id));
    }

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(orders.values());
    }

    @Override
    public List<Order> findByCustomerEmail(String customerEmail) {
        return orders.values().stream()
                .filter(order -> order.getCustomerEmail().equals(customerEmail))
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findByCustomerEmailAndDate(String customerEmail, LocalDateTime transactionDate) {
        return orders.values().stream()
                .filter(order -> order.getCustomerEmail().equals(customerEmail) &&
                        order.getTransactionDate().equals(transactionDate))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        orders.remove(id);
    }
}