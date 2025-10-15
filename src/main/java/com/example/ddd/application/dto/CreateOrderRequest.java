// src/main/java/com/example/ddd/application/dto/CreateOrderRequest.java
package com.example.ddd.application.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateOrderRequest {
    @NotBlank
    private String customerId;

    private String street;
    private String city;
    private String postalCode;

    // getters and setters
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
}

// src/main/java/com/example/ddd/application/dto/AddItemRequest.java


// src/main/java/com/example/ddd/application/OrderController.java
