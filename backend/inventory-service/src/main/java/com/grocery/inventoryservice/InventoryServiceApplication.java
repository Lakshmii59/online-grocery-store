package com.grocery.inventoryservice;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication(scanBasePackages = "com.grocery")
@OpenAPIDefinition(
        info = @Info(
                title = "Online Grocery Store - Inventory Microservice REST API Documentation",
                description = "REST APIs for managing grocery inventory including CRUD operations, stock reservation, release and confirmation.",
                version = "v1"
        ),
        externalDocs = @ExternalDocumentation(
                description = "Online Grocery Store Inventory API Documentation"
        )
)
public class InventoryServiceApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
        SpringApplication.run(InventoryServiceApplication.class, args);
    }
}
