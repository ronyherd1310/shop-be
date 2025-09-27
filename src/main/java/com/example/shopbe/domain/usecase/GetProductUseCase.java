package com.example.shopbe.domain.usecase;

import com.example.shopbe.domain.entity.Product;

import java.util.Optional;

public interface GetProductUseCase {
    Optional<Product> getProductById(Long id);
}