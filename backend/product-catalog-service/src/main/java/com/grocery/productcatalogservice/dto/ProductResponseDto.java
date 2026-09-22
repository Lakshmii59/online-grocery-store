package com.grocery.productcatalogservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record ProductResponseDto(

        @Schema(description = "Product ID", example = "1")
        Long productId,

        @Schema(description = "Product Name", example = "Apple")
        String productName,

        @Schema(description = "Product SKU", example = "APL001")
        String sku,

        @Schema(description = "Price", example = "120.50")
        BigDecimal price,

        @Schema(description = "Product Status", example = "true")
        Boolean active,

        @Schema(description = "Product Image URL", example = "/images/apple.jpg")
        String imageUrl,

        @Schema(description = "Category ID", example = "1")
        Long categoryId

) {}