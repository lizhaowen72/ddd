package com.example.ddd.application;

import com.example.ddd.application.dto.AddItemRequest;
import com.example.ddd.application.dto.CreateOrderRequest;
import com.example.ddd.domain.model.Address;
import com.example.ddd.domain.model.Order;
import com.example.ddd.domain.model.OrderId;
import com.example.ddd.domain.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createOrder(@RequestBody CreateOrderRequest request) {
        Address address = new Address(request.getStreet(), request.getCity(), request.getPostalCode());
        OrderId orderId = orderService.createOrder(request.getCustomerId(), address);

        return ResponseEntity.ok(Map.of("orderId", orderId.getId()));
    }

    @PostMapping("/{orderId}/items")
    public ResponseEntity<Void> addItem(
            @PathVariable String orderId,
            @RequestBody AddItemRequest request) {

        orderService.addItemToOrder(
                new OrderId(orderId),
                request.getProductId(),
                request.getProductName(),
                request.getQuantity(),
                request.getUnitPrice()
        );

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{orderId}/items/{productId}")
    public ResponseEntity<Void> removeItem(
            @PathVariable String orderId,
            @PathVariable String productId) {

        orderService.removeItemFromOrder(new OrderId(orderId), productId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{orderId}/confirm")
    public ResponseEntity<Void> confirmOrder(@PathVariable String orderId) {
        orderService.confirmOrder(new OrderId(orderId));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable String orderId) {
        Order order = orderService.getOrder(new OrderId(orderId));
        return ResponseEntity.ok(order);
    }
}