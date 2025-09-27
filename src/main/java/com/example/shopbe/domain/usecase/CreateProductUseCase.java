package com.example.shopbe.domain.usecase;

import com.example.shopbe.domain.entity.Product;

import java.math.BigDecimal;

public interface CreateProductUseCase {
    Product createProduct(String name, String description, BigDecimal price);
}