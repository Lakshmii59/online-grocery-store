package com.grocery.orderservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record ProductDto (
    @Schema(description = "Product Id",
            example = "1")
     Long productId,

    @Schema(description = "Product name",
            example = "Fruits")
     String productName,

    @Schema(description = "Sku code",
            example = "AP001")
     String sku,

    @Schema(description = "Product Price",
            example = "120.00")
     BigDecimal price,

    @Schema(description = "Available Quantity of product",
            example = "50")
     Integer availableQuantity,

    @Schema(description = "Status of product",
            example = "active")
     Boolean active,

    @Schema(description = "Customer Id",
            example = "1")
     Long categoryId,

    @Schema(description = "Product description",
            example = "Fresh fruits")
     String description
){}
