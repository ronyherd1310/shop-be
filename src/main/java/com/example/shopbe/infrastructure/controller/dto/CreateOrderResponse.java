package com.example.shopbe.infrastructure.controller.dto;

import java.util.List;

public record CreateOrderResponse(
    String customerName,
    String customerEmail,
    List<CreateOrderItemRequest> items,
    String errorMessage
) {}