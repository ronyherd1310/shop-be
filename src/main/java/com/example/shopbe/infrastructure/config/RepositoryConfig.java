package com.example.shopbe.infrastructure.config;

import com.example.shopbe.domain.repository.OrderRepository;
import com.example.shopbe.domain.repository.ProductRepository;
import com.example.shopbe.infrastructure.repository.InMemoryOrderRepository;
import com.example.shopbe.infrastructure.repository.InMemoryProductRepository;
import com.example.shopbe.infrastructure.repository.JpaOrderRepository;
import com.example.shopbe.infrastructure.repository.JpaProductRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RepositoryConfig {

    @Bean
    @Primary
    @ConditionalOnProperty(name = "app.repository.type", havingValue = "jpa", matchIfMissing = true)
    public ProductRepository productRepository(JpaProductRepository jpaProductRepository) {
        return jpaProductRepository;
    }

    @Bean
    @ConditionalOnProperty(name = "app.repository.type", havingValue = "memory")
    public ProductRepository inMemoryProductRepository() {
        return new InMemoryProductRepository();
    }

    @Bean
    @Primary
    @ConditionalOnProperty(name = "app.repository.type", havingValue = "jpa", matchIfMissing = true)
    public OrderRepository orderRepository(JpaOrderRepository jpaOrderRepository) {
        return jpaOrderRepository;
    }

    @Bean
    @ConditionalOnProperty(name = "app.repository.type", havingValue = "memory")
    public OrderRepository inMemoryOrderRepository() {
        return new InMemoryOrderRepository();
    }
}