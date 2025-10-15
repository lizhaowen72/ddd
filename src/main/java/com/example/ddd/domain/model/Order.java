// src/main/java/com/example/ddd/domain/model/Order.java
package com.example.ddd.domain.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @EmbeddedId
    private OrderId id;

    @Embedded
    private Address shippingAddress;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "total_amount"))
    private Money totalAmount;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private List<OrderLine> orderLines = new ArrayList<>();

    private String customerId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    protected Order() {}

    public Order(OrderId id, String customerId, Address shippingAddress) {
        this.id = id;
        this.customerId = customerId;
        this.shippingAddress = shippingAddress;
        this.totalAmount = Money.ZERO;
        this.status = OrderStatus.DRAFT;
    }

    // 核心业务方法
    public void addItem(String productId, String productName, int quantity, Money unitPrice) {
        if (status != OrderStatus.DRAFT) {
            throw new IllegalStateException("Cannot modify order in status: " + status);
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        // 查找是否已存在相同商品
        orderLines.stream()
                .filter(line -> line.getProductId().equals(productId))
                .findFirst()
                .ifPresentOrElse(
                        existingLine -> existingLine.increaseQuantity(quantity),
                        () -> orderLines.add(new OrderLine(productId, productName, quantity, unitPrice))
                );

        recalculateTotal();
    }

    public void removeItem(String productId) {
        if (status != OrderStatus.DRAFT) {
            throw new IllegalStateException("Cannot modify order in status: " + status);
        }

        boolean removed = orderLines.removeIf(line -> line.getProductId().equals(productId));
        if (removed) {
            recalculateTotal();
        }
    }

    public void confirm() {
        if (orderLines.isEmpty()) {
            throw new IllegalStateException("Cannot confirm empty order");
        }
        if (totalAmount.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalStateException("Order total must be positive");
        }
        this.status = OrderStatus.CONFIRMED;
    }

    public void cancel() {
        this.status = OrderStatus.CANCELLED;
    }

    private void recalculateTotal() {
        this.totalAmount = orderLines.stream()
                .map(OrderLine::getSubtotal)
                .reduce(Money.ZERO, Money::add);
    }

    // 查询方法
    public OrderId getId() { return id; }
    public Address getShippingAddress() { return shippingAddress; }
    public Money getTotalAmount() { return totalAmount; }
    public String getCustomerId() { return customerId; }
    public OrderStatus getStatus() { return status; }

    // 返回不可修改的列表，保护聚合内部状态
    public List<OrderLine> getOrderLines() {
        return Collections.unmodifiableList(orderLines);
    }

    public enum OrderStatus {
        DRAFT, CONFIRMED, CANCELLED
    }
}