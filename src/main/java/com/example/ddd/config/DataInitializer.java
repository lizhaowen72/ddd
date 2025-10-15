// src/main/java/com/example/ddd/config/DataInitializer.java
package com.example.ddd.config;

import com.example.ddd.domain.model.Address;
import com.example.ddd.domain.service.OrderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    private final OrderService orderService;

    public DataInitializer(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void run(String... args) throws Exception {
        // 创建测试订单
        Address address = new Address("123 Main St", "New York", "10001");
        var orderId = orderService.createOrder("customer-123", address);

        // 添加商品
        orderService.addItemToOrder(orderId, "prod-1", "Laptop", 1, new BigDecimal("999.99"));
        orderService.addItemToOrder(orderId, "prod-2", "Mouse", 2, new BigDecimal("29.99"));

        System.out.println("Test order created: " + orderId.getId());
    }
}