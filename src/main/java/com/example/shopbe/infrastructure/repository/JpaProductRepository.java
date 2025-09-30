package com.example.shopbe.infrastructure.repository;

import com.example.shopbe.domain.entity.Product;
import com.example.shopbe.domain.repository.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaProductRepository extends JpaRepository<Product, Long>, ProductRepository {

    @Override
    default Product save(Product product) {
        return saveAndFlush(product);
    }

    @Override
    Optional<Product> findById(Long id);

    @Override
    List<Product> findAll();

    @Override
    void deleteById(Long id);
}