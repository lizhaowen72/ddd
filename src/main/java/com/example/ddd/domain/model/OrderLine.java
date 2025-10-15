// src/main/java/com/example/ddd/domain/model/OrderLine.java
package com.example.ddd.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "order_lines")
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productId;
    private String productName;
    private int quantity;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "unit_price"))
    private Money unitPrice;

    protected OrderLine() {}

    public OrderLine(String productId, String productName, int quantity, Money unitPrice) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Money getSubtotal() {
        return unitPrice.multiply(quantity);
    }

    public void increaseQuantity(int additionalQuantity) {
        if (additionalQuantity <= 0) {
            throw new IllegalArgumentException("Additional quantity must be positive");
        }
        this.quantity += additionalQuantity;
    }

    // getters
    public Long getId() { return id; }
    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public Money getUnitPrice() { return unitPrice; }
}