package com.grocery.orderservice;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.TimeZone;

@SpringBootApplication(scanBasePackages = "com.grocery")
@EnableFeignClients
@OpenAPIDefinition(
        info = @Info(
                title = "Online Grocery Store - Order Management Microservice REST API Documentation",
                description = "REST APIs for managing grocery orders,customers, order items,order status,inventory reservation and order processing.",
                version = "v1"
        ),
        externalDocs = @ExternalDocumentation(
                description = "Online Grocery Store Order Service API Documentation"
        )
)
public class OrderServiceApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
