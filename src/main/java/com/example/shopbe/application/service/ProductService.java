package com.example.shopbe.application.service;

import com.example.shopbe.domain.entity.Product;
import com.example.shopbe.domain.repository.ProductRepository;
import com.example.shopbe.domain.usecase.CreateProductUseCase;
import com.example.shopbe.domain.usecase.GetProductUseCase;
import com.example.shopbe.domain.usecase.ListProductsUseCase;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService implements CreateProductUseCase, GetProductUseCase, ListProductsUseCase {

    private final ProductRepository productRepository;
    private final AtomicLong idGenerator = new AtomicLong(1);

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(String name, String description, BigDecimal price, String url) {
        Long id = idGenerator.getAndIncrement();
        Product product = new Product(id, name, description, price, url);
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}