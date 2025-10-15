// src/main/java/com/example/ddd/domain/repository/OrderRepository.java
package com.example.ddd.domain.repository;

import com.example.ddd.domain.model.Order;
import com.example.ddd.domain.model.OrderId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, OrderId> {

    Optional<Order> findById(OrderId orderId);

    List<Order> findByCustomerId(String customerId);

    List<Order> findByCustomerIdAndStatus(String customerId, Order.OrderStatus status);

    @Query("SELECT o FROM Order o WHERE o.totalAmount.amount > :minAmount")
    List<Order> findOrdersWithTotalGreaterThan(@Param("minAmount") BigDecimal minAmount);

    boolean existsById(OrderId orderId);
}