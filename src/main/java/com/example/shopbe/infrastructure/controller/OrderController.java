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
import com.example.shopbe.infrastructure.controller.dto.CreateOrderResponse;
import com.example.shopbe.infrastructure.util.InputSanitizer;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final GetOrderUseCase getOrderUseCase;
    private final ListOrdersUseCase listOrdersUseCase;
    private final GetProductUseCase getProductUseCase;
    private final InputSanitizer inputSanitizer;

    public OrderController(CreateOrderUseCase createOrderUseCase,
                          GetOrderUseCase getOrderUseCase,
                          ListOrdersUseCase listOrdersUseCase,
                          GetProductUseCase getProductUseCase,
                          InputSanitizer inputSanitizer) {
        this.createOrderUseCase = createOrderUseCase;
        this.getOrderUseCase = getOrderUseCase;
        this.listOrdersUseCase = listOrdersUseCase;
        this.getProductUseCase = getProductUseCase;
        this.inputSanitizer = inputSanitizer;
    }

    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CreateOrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        try {
            // Sanitize inputs
            String sanitizedName = inputSanitizer.sanitize(request.customerName());
            String sanitizedEmail = inputSanitizer.sanitizeEmail(request.customerEmail());

            Order order = createOrderUseCase.createOrder(
                sanitizedName,
                sanitizedEmail,
                convertToOrderItems(request.items())
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToOrderResponse(order,""));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(convertToOrderResponse(new Order(), e.getMessage()));
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
        Product product = getProductUseCase.getProductById(itemRequest.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + itemRequest.productId()));

        return new OrderItem(
                product.id,
                product.name,
                product.price,
                itemRequest.quantity()
        );
    }

    private CreateOrderResponse convertToOrderResponse(Order order, String errorMessage) {
        List<CreateOrderItemRequest> responseItems = Optional.ofNullable(order)
                .map(o -> o.items)
                .map(items -> items.stream()
                        .map(item -> new CreateOrderItemRequest(item.productId, item.quantity))
                        .collect(Collectors.toList()))
                .orElse(List.of());

        return new CreateOrderResponse(
                Optional.ofNullable(order).map(o -> o.customerName).orElse(null),
                Optional.ofNullable(order).map(o -> o.customerEmail).orElse(null),
                responseItems,
                errorMessage
        );
    }
}