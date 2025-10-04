package com.example.shopbe.infrastructure.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateOrderRequest(
    @NotBlank(message = "Customer name is required")
    @Size(min = 2, max = 100, message = "Customer name must be between 2 and 100 characters")
    String customerName,

    @NotBlank(message = "Customer email is required")
    @Email(message = "Customer email must be valid")
    String customerEmail,

    @NotEmpty(message = "Order must contain at least one item")
    @Size(max = 100, message = "Order cannot exceed 100 items")
    @Valid
    List<CreateOrderItemRequest> items
) {}