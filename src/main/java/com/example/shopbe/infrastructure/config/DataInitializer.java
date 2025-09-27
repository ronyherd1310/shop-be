package com.example.shopbe.infrastructure.config;

import com.example.shopbe.domain.entity.OrderItem;
import com.example.shopbe.domain.usecase.CreateOrderUseCase;
import com.example.shopbe.domain.usecase.CreateProductUseCase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CreateProductUseCase createProductUseCase;
    private final CreateOrderUseCase createOrderUseCase;

    public DataInitializer(CreateProductUseCase createProductUseCase, CreateOrderUseCase createOrderUseCase) {
        this.createProductUseCase = createProductUseCase;
        this.createOrderUseCase = createOrderUseCase;
    }

    @Override
    public void run(String... args) throws Exception {
        initializeProducts();
        initializeOrders();
    }

    private void initializeProducts() {
        createProductUseCase.createProduct(
                "Gaming Laptop",
                "High-performance gaming laptop with RTX 4070 graphics card",
                new BigDecimal("1299.99")
        );

        createProductUseCase.createProduct(
                "Wireless Headphones",
                "Premium noise-cancelling wireless headphones with 30-hour battery",
                new BigDecimal("249.99")
        );

        createProductUseCase.createProduct(
                "Smartphone",
                "Latest flagship smartphone with 128GB storage and triple camera",
                new BigDecimal("799.99")
        );

        createProductUseCase.createProduct(
                "Mechanical Keyboard",
                "RGB backlit mechanical keyboard with Cherry MX Blue switches",
                new BigDecimal("149.99")
        );

        createProductUseCase.createProduct(
                "4K Monitor",
                "27-inch 4K UHD monitor with IPS panel and USB-C connectivity",
                new BigDecimal("449.99")
        );

        createProductUseCase.createProduct(
                "Gaming Mouse",
                "Ergonomic gaming mouse with 16000 DPI sensor and customizable buttons",
                new BigDecimal("79.99")
        );

        createProductUseCase.createProduct(
                "Tablet",
                "10-inch tablet with 64GB storage, perfect for work and entertainment",
                new BigDecimal("329.99")
        );

        createProductUseCase.createProduct(
                "Bluetooth Speaker",
                "Portable waterproof Bluetooth speaker with 12-hour battery life",
                new BigDecimal("89.99")
        );

        createProductUseCase.createProduct(
                "Webcam",
                "HD webcam with auto-focus and built-in microphone for video calls",
                new BigDecimal("59.99")
        );

        createProductUseCase.createProduct(
                "Desk Chair",
                "Ergonomic office chair with lumbar support and adjustable height",
                new BigDecimal("199.99")
        );

        System.out.println("Initialized 10 dummy products successfully!");
    }

    private void initializeOrders() {
        createOrderUseCase.createOrder(
                "John Doe",
                "john.doe@example.com",
                Arrays.asList(
                        new OrderItem(1L, "Gaming Laptop", new BigDecimal("1299.99"), 1),
                        new OrderItem(4L, "Mechanical Keyboard", new BigDecimal("149.99"), 1)
                )
        );

        createOrderUseCase.createOrder(
                "Jane Smith",
                "jane.smith@example.com",
                Arrays.asList(
                        new OrderItem(2L, "Wireless Headphones", new BigDecimal("249.99"), 1),
                        new OrderItem(8L, "Bluetooth Speaker", new BigDecimal("89.99"), 2)
                )
        );

        createOrderUseCase.createOrder(
                "Mike Johnson",
                "mike.johnson@example.com",
                Arrays.asList(
                        new OrderItem(3L, "Smartphone", new BigDecimal("799.99"), 1)
                )
        );

        createOrderUseCase.createOrder(
                "Sarah Wilson",
                "sarah.wilson@example.com",
                Arrays.asList(
                        new OrderItem(5L, "4K Monitor", new BigDecimal("449.99"), 2),
                        new OrderItem(6L, "Gaming Mouse", new BigDecimal("79.99"), 1),
                        new OrderItem(10L, "Desk Chair", new BigDecimal("199.99"), 1)
                )
        );

        createOrderUseCase.createOrder(
                "David Brown",
                "david.brown@example.com",
                Arrays.asList(
                        new OrderItem(7L, "Tablet", new BigDecimal("329.99"), 1),
                        new OrderItem(9L, "Webcam", new BigDecimal("59.99"), 1)
                )
        );

        System.out.println("Initialized 5 sample orders successfully!");
    }
}