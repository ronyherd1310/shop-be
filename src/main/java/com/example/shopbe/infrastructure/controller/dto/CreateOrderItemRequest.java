package com.example.shopbe.infrastructure.controller.dto;

public record CreateOrderItemRequest(
    Long productId,
    Integer quantity
) {}