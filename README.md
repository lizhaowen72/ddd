# DDD Order Example with Spring Boot

A practical example of Domain-Driven Design (DDD) implementation using Spring Boot, demonstrating aggregate design, bounded contexts, and preventing aggregate bloating in a simplified e-commerce domain.

## 📋 Overview

This project showcases how to properly design aggregates in DDD while maintaining clear boundaries and preventing the common pitfall of aggregate bloating. The example focuses on an Order domain with proper separation of concerns.

## 🏗️ Architecture

### Domain Model Structure
```
com.example.ecommerce
├── application          // Application Layer
│   ├── OrderApplicationService
│   └── dto              // Data Transfer Objects
├── domain               // Domain Layer (Core!)
│   ├── model
│   │   ├── Order.java          (Aggregate Root)
│   │   ├── OrderLine.java      (Entity within Aggregate)
│   │   ├── OrderId.java        (Value Object)
│   │   ├── Money.java          (Value Object)
│   │   └── Address.java        (Value Object)
│   ├── service
│   │   └── OrderService.java   (Domain Service)
│   └── repository
│       └── OrderRepository.java (Repository Interface)
├── infrastructure       // Infrastructure Layer
└── interfaces          // Interface Layer
    └── web
        └── OrderController.java
```

## 🎯 Key DDD Concepts Demonstrated

### Aggregate Design
- **Aggregate Root**: `Order`
- **Internal Entities**: `OrderLine` 
- **Value Objects**: `Money`, `Address`, `OrderId`
- **External References**: `customerId`, `productId` (via ID references)

### Preventing Aggregate Bloating
- Clear consistency boundaries
- Reference external aggregates by ID only
- Rich domain model with encapsulated business logic
- Proper use of value objects for concepts without identity

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Spring Boot 3.1.0

### Running the Application

1. **Clone the repository**
   ```bash
   git clone <your-repository-url>
   cd ddd-order-example
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - Application: http://localhost:8080
   - H2 Database Console: http://localhost:8080/h2-console
     - JDBC URL: `jdbc:h2:mem:testdb`
     - Username: `sa`
     - Password: (leave empty)

## 📚 API Examples

### Create an Order
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "customer-123",
    "street": "123 Main Street",
    "city": "New York",
    "postalCode": "10001"
  }'
```

### Add Item to Order
```bash
curl -X POST http://localhost:8080/api/orders/ORDER-{order-id}/items \
  -H "Content-Type: application/json" \
  -d '{
    "productId": "prod-1",
    "productName": "Laptop",
    "quantity": 1,
    "unitPrice": 999.99
  }'
```

### Confirm Order
```bash
curl -X POST http://localhost:8080/api/orders/ORDER-{order-id}/confirm
```

### Get Order Details
```bash
curl http://localhost:8080/api/orders/ORDER-{order-id}
```

## 💡 Key Features

### Rich Domain Model
- Business logic encapsulated within domain objects
- Invariants protected at aggregate boundaries
- Value objects for type safety and validation

### Proper Aggregate Design
- Single responsibility for Order aggregate
- OrderLine as internal entity with local identity
- External aggregates referenced by ID only

### Spring Boot Integration
- JPA with H2 in-memory database
- Spring Data JPA repositories
- RESTful API with proper error handling
- Automatic test data initialization

## 🛠️ Technical Stack

- **Framework**: Spring Boot 3.1.0
- **Database**: H2 (in-memory)
- **Persistence**: Spring Data JPA
- **Validation**: Bean Validation
- **Testing**: Spring Boot Test
- **Build Tool**: Maven

## 🧪 Testing

Run the test suite:
```bash
mvn test
```

The project includes:
- Unit tests for domain objects
- Integration tests for repositories
- API tests for controllers

## 📖 Learning Resources

This example demonstrates:

1. **Aggregate Design**: How to identify and design proper aggregates
2. **Bounded Contexts**: Clear separation between domain concepts
3. **Anti-Corruption Layers**: Protecting the domain from external influences
4. **Repository Pattern**: Proper data access abstraction
5. **Domain Events**: Handling cross-aggregate communication

## 🤝 Contributing

Feel free to submit issues and enhancement requests! 

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

**Note**: This is a simplified example for educational purposes. Real-world applications would require additional considerations for security, performance, and production readiness.
