# Online Grocery Store - Microservices
 
## Overview
 
The **Online Grocery Store** is a Spring Boot Microservices application developed as part of the **Smartek21 Java Assessment**.
 
The project follows a microservices architecture where each service has its own database and communicates using **Spring Cloud OpenFeign** and **Eureka Service Discovery**.
 
---
 
# Technologies Used
 
- Java 17
- Spring Boot 3.x
- Spring Cloud
- Spring Data JPA
- PostgreSQL
- Maven
- Docker
- Eureka Server
- Spring Cloud Config Server
- OpenFeign
- Swagger / OpenAPI
- Lombok
- JUnit 5
- Mockito
 
---
 
# Project Architecture
 
```
                    Config Server
                          |
                          |
                  Eureka Server
                    /     |      \
                   /      |       \
                  /       |        \
Product Catalog  Inventory Service  Order Service
        |                |                |
   product_db      inventory_db       order_db
```
 
---
 
# Microservices
 
## Config Server
 
- Centralized configuration management.
 
## Eureka Server
 
- Service Registration
- Service Discovery
 
## Product Catalog Service
 
- Category CRUD
- Product CRUD
- Product Validation
- Swagger Documentation
 
## Inventory Service
 
- Inventory CRUD
- Reserve Inventory
- Release Inventory
- Confirm Inventory
- Stock Movement Tracking
 
## Order Service
 
- Customer CRUD
- Order Creation
- Order Cancellation
- Product Validation
- Inventory Validation
- Order Total Calculation
 
---
 
# Features
 
## Product Service
 
- Create Product
- Update Product
- Delete Product
- Get Product By Id
- Get All Products
- Category Management
- Product Active/Inactive Validation
 
---
 
## Inventory Service
 
- Create Inventory
- Update Inventory
- Delete Inventory
- Get Inventory
- Reserve Inventory
- Release Inventory
- Confirm Inventory
- Track Stock Movements
 
---
 
## Order Service
 
- Create Customer
- Create Order
- Cancel Order
- Calculate Total Amount
- Fetch Product Details
- Check Inventory Availability
- Reserve Inventory
- Prevent Ordering Inactive Products
- Confirm Inventory
 
---
 
# Business Rules
 
## Product Rules
 
- Product price must be greater than zero.
- Product cannot be ordered if inactive.
- Category must exist before creating a product.
 
## Inventory Rules
 
- Inventory must exist before product ordering.
- Available quantity cannot be negative.
- Reserved quantity cannot be negative.
- Order creation reserves inventory.
- Order cancellation releases reserved inventory.
- Order confirmation deducts reserved inventory.
 
## Order Rules
 
- Order must contain at least one item.
- Quantity must be greater than zero.
- Total amount is calculated in backend.
- Product details are fetched from Product Service.
- Inventory is validated through Inventory Service.
- Product snapshot (name & price) is stored in Order Items.
 
---
 
# Database Setup
 
Create the following PostgreSQL databases:
 
- product_db
- inventory_db
- order_db
 
---
 
# Database Tables
 
## product_db
 
- category
- product
 
## inventory_db
 
- inventory
- stock_movement
 
## order_db
 
- customer
- grocery_order
- order_item
 
---
 
# Running the Application
 
Start the services in the following order:
 
1. Eureka Server
   - Port: **8761**
 
2. Config Server
   - Port: **8071**
 
3. Product Catalog Service
   - Port: **8083**
 
4. Inventory Service
   - Port: **8082**
 
5. Order Service
   - Port: **8084**
 
---
 
# Verify Services
 
## Eureka Dashboard
 
```
http://localhost:8761
```
 
Registered Services:
 
- CONFIG-SERVER
- PRODUCT-CATALOG-SERVICE
- INVENTORY-SERVICE
- ORDER-SERVICE
 
---
 
# Swagger Documentation
 
### Product Catalog Service
 
```
http://localhost:8083/swagger-ui/index.html
```
 
### Inventory Service
 
```
http://localhost:8082/swagger-ui/index.html
```
 
### Order Service
 
```
http://localhost:8084/swagger-ui/index.html
```
 
---
 
# OpenAPI Documentation
 
### Product Service
 
```
http://localhost:8083/v3/api-docs
```
 
### Inventory Service
 
```
http://localhost:8082/v3/api-docs
```
 
### Order Service
 
```
http://localhost:8084/v3/api-docs
```
 
---
 
# Inter-Service Communication
 
The Order Service communicates with:
 
- Product Catalog Service using OpenFeign to fetch product details.
- Inventory Service using OpenFeign to check stock availability, reserve stock, release stock, and confirm inventory.
 
---
 
# Testing
 
The project has been tested using:
 
- Postman
- Swagger UI
- JUnit 5
- Mockito
- Integration Tests
 
---
 
# Project Structure
 
```
online-grocery-store
│
├── config-server
├── eureka-server
├── common-library
├── product-catalog-service
├── inventory-service
├── order-service
└── README.md
```
 
---
 
# Future Enhancements
 
- Kafka-based asynchronous communication
- API Gateway
- Circuit Breaker (Resilience4j)
- Distributed Tracing
- Prometheus & Grafana Monitoring
- Docker Compose Deployment
 
---