package com.example.shopbe.domain.usecase;

import com.example.shopbe.domain.entity.Product;

import java.util.List;

public interface ListProductsUseCase {
    List<Product> getAllProducts();
}