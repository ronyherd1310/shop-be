package com.example.shopbe.infrastructure.repository;

import com.example.shopbe.domain.entity.Order;
import com.example.shopbe.domain.repository.OrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface JpaOrderRepository extends JpaRepository<Order, Long>, OrderRepository {

    @Override
    default Order save(Order order) {
        return saveAndFlush(order);
    }

    @Override
    Optional<Order> findById(Long id);

    @Override
    List<Order> findAll();

    @Override
    @Query("SELECT o FROM Order o WHERE o.customerEmail = :customerEmail")
    List<Order> findByCustomerEmail(@Param("customerEmail") String customerEmail);

    @Override
    @Query("SELECT o FROM Order o WHERE o.customerEmail = :customerEmail AND DATE(o.transactionDate) = DATE(:transactionDate)")
    List<Order> findByCustomerEmailAndDate(@Param("customerEmail") String customerEmail,
                                         @Param("transactionDate") LocalDateTime transactionDate);

    @Override
    void deleteById(Long id);
}