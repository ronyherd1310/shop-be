package com.example.shopbe.infrastructure.controller;

import com.example.shopbe.domain.entity.Order;
import com.example.shopbe.domain.entity.OrderItem;
import com.example.shopbe.domain.entity.Product;
import com.example.shopbe.domain.usecase.CreateOrderUseCase;
import com.example.shopbe.domain.usecase.GetOrderUseCase;
import com.example.shopbe.domain.usecase.GetProductUseCase;
import com.example.shopbe.domain.usecase.ListOrdersUseCase;
import com.example.shopbe.infrastructure.controller.dto.CreateOrderItemRequest;
import com.example.shopbe.infrastructure.controller.dto.CreateOrderRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final GetOrderUseCase getOrderUseCase;
    private final ListOrdersUseCase listOrdersUseCase;
    private final GetProductUseCase getProductUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase,
                          GetOrderUseCase getOrderUseCase,
                          ListOrdersUseCase listOrdersUseCase,
                          GetProductUseCase getProductUseCase) {
        this.createOrderUseCase = createOrderUseCase;
        this.getOrderUseCase = getOrderUseCase;
        this.listOrdersUseCase = listOrdersUseCase;
        this.getProductUseCase = getProductUseCase;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest request) {
        try {
            Order order = createOrderUseCase.createOrder(
                request.getCustomerName(),
                request.getCustomerEmail(),
                convertToOrderItems(request.getItems())
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(order);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        return getOrderUseCase.getOrderById(id)
                .map(order -> ResponseEntity.ok(order))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = listOrdersUseCase.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/customer/{email}")
    public ResponseEntity<List<Order>> getOrdersByCustomer(@PathVariable String email) {
        List<Order> orders = getOrderUseCase.getOrdersByCustomerEmail(email);
        return ResponseEntity.ok(orders);
    }

    private List<OrderItem> convertToOrderItems(List<CreateOrderItemRequest> itemRequests) {
        return itemRequests.stream()
                .map(this::convertToOrderItem)
                .collect(Collectors.toList());
    }

    private OrderItem convertToOrderItem(CreateOrderItemRequest itemRequest) {
        Product product = getProductUseCase.getProductById(itemRequest.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + itemRequest.getProductId()));

        return new OrderItem(
                product.getId(),
                product.getName(),
                product.getPrice(),
                itemRequest.getQuantity()
        );
    }
}