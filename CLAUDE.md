# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview
This is a Spring Boot e-commerce backend application implementing Clean Architecture patterns. The application provides REST APIs for managing products and orders with an in-memory data store.

## Development Commands

### Build and Run
```bash
# Build the project
mvn clean compile

# Run tests
mvn test

# Build JAR file
mvn clean package

# Run the application
mvn spring-boot:run
# Or run the JAR directly:
java -jar target/shop-be-0.0.1-SNAPSHOT.jar
```

### Development Server
The application runs on port 8080 by default. API endpoints are available at:
- Products: `/api/products`
- Orders: `/api/orders`
- Greeting (sample): `/api/greeting`

## Architecture Overview

This project follows Clean Architecture with clear separation of concerns:

### Layer Structure
- **Domain Layer** (`src/main/java/com/example/shopbe/domain/`)
  - `entity/`: Core business entities (Product, Order, OrderItem, OrderStatus)
  - `repository/`: Repository interfaces (ProductRepository, OrderRepository)
  - `usecase/`: Business logic use cases (Create*, Get*, List*)

- **Application Layer** (`src/main/java/com/example/shopbe/application/`)
  - `service/`: Application services that orchestrate use cases

- **Infrastructure Layer** (`src/main/java/com/example/shopbe/infrastructure/`)
  - `controller/`: REST controllers and DTOs
  - `repository/`: Repository implementations (InMemoryProductRepository, InMemoryOrderRepository)
  - `config/`: Configuration classes (DataInitializer)

### Key Patterns
- **Dependency Injection**: Controllers depend on use cases, use cases depend on repository interfaces
- **Immutable Entities**: All domain entities use final fields and constructor injection
- **Repository Pattern**: Abstract data access through interfaces in domain layer
- **Use Case Pattern**: Each business operation is encapsulated in a dedicated use case class

### Data Flow
1. HTTP requests → Controllers (infrastructure)
2. Controllers → Use Cases (domain)
3. Use Cases → Repository interfaces (domain)
4. Repository implementations (infrastructure) handle data persistence

## Key Implementation Notes
- All entities are immutable with final fields
- In-memory repositories use ConcurrentHashMap for thread safety
- Use cases are stateless and can be safely injected as Spring beans
- DTOs are used for request/response mapping in controllers
- Sample data is initialized via DataInitializer component

## Code Quality Patterns

### Method Extraction Refactoring
The codebase follows clean code principles with well-extracted methods:

**OrderService Architecture:**
- `createOrder()`: Main orchestration method handling business flow using ternary operator for concise branching
- `createNewOrder()`: Extracted method for new order creation with proper defaults
- `updateExistingOrder()`: Extracted method for updating existing orders with new items
- `convertItemsToProductQuantityMap()`: Utility method converting item lists to product-quantity maps
- `calculateTotalAmount()`: Utility method for order total calculations

**OrderController Architecture:**
- `createOrder()`: HTTP endpoint with clean exception handling and minimal logic
- `convertToOrderItems()`: Functional method using streams for DTO to entity conversion
- `convertToOrderItem()`: Single-responsibility method for individual item conversion with proper error handling

### Order Management Logic
- **New Orders**: Created with PENDING status and current timestamp
- **Existing Orders**: Updated by merging new item quantities with existing ones
- **Quantity Handling**: Uses `Map.getOrDefault()` for safe quantity retrieval, preventing null pointer exceptions
- **Total Recalculation**: Automatically recalculates totals when updating existing orders

### Best Practices Applied
- **Single Responsibility**: Each method has one clear purpose
- **Extract Method**: Complex logic extracted into well-named private methods
- **Functional Programming**: Using Java streams for cleaner data transformations
- **Concise Control Flow**: Ternary operators for simple conditional logic
- **Proper Error Handling**: Using `orElseThrow()` for clean exception handling
- **Null Safety**: Using `getOrDefault()` instead of direct map access
- **Type Safety**: Proper generic types (e.g., `Map<Long, Integer>`) instead of raw types
- **Immutable Design**: Entities remain immutable, new instances created for updates

### Controller Layer Patterns
- **Thin Controllers**: Controllers handle only HTTP concerns, delegate business logic to services
- **DTO Conversion**: Clean separation between external DTOs and internal domain entities
- **Stream Processing**: Functional approach for collections processing using `map()` and `collect()`
- **Exception Translation**: Business exceptions properly translated to HTTP responses
- **Method References**: Using `this::methodName` for cleaner functional code