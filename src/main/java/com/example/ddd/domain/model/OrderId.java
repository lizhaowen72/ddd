// src/main/java/com/example/ddd/domain/model/OrderId.java
package com.example.ddd.domain.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class OrderId implements Serializable {
    private String id;

    protected OrderId() {}

    public OrderId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be empty");
        }
        this.id = id;
    }

    public static OrderId generate() {
        return new OrderId("ORDER-" + UUID.randomUUID().toString());
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderId orderId = (OrderId) o;
        return Objects.equals(id, orderId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id;
    }
}