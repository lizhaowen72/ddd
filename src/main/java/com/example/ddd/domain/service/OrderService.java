// src/main/java/com/example/ddd/domain/service/OrderService.java
package com.example.ddd.domain.service;

import com.example.ddd.domain.model.*;
import com.example.ddd.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderId createOrder(String customerId, Address shippingAddress) {
        OrderId orderId = OrderId.generate();
        Order order = new Order(orderId, customerId, shippingAddress);
        return orderRepository.save(order).getId();
    }

    public void addItemToOrder(OrderId orderId, String productId, String productName,
                               int quantity, BigDecimal unitPrice) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        Money price = new Money(unitPrice);
        order.addItem(productId, productName, quantity, price);

        orderRepository.save(order);
    }

    public void removeItemFromOrder(OrderId orderId, String productId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        order.removeItem(productId);
        orderRepository.save(order);
    }

    public void confirmOrder(OrderId orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        order.confirm();
        orderRepository.save(order);
    }

    public void cancelOrder(OrderId orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        order.cancel();
        orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public Order getOrder(OrderId orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }
}

// 自定义异常
class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(OrderId orderId) {
        super("Order not found: " + orderId);
    }
}