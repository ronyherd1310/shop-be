package com.example.shopbe.infrastructure.controller.dto;

import java.math.BigDecimal;

public record CreateProductRequest(
    String name,
    String description,
    BigDecimal price
) {}