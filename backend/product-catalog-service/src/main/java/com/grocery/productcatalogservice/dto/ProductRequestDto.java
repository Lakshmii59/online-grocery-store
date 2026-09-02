package com.grocery.productcatalogservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(
        name = "Product Request",
        description = "Product request details"
)
public record ProductRequestDto(

        @NotBlank(message = "Product name is required")
        @Schema(description = "Product Name", example = "Apple")
        String productName,

        @NotBlank(message = "SKU is required")
        @Schema(description = "Product SKU", example = "APL001")
        String sku,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = false)
        @Schema(description = "Price", example = "120.50")
        BigDecimal price,

        @NotNull
        @Min(0)
        @Schema(description = "Available Quantity", example = "100")
        Integer availableQuantity,

        @NotNull
        @Schema(description = "Product Status", example = "true")
        Boolean active,

        @NotBlank(message = "Image URL is required")
        @Schema(description = "Product Image URL", example = "/images/apple.jpg")
        String imageUrl,

        @NotNull
        @Schema(description = "Category ID", example = "1")
        Long categoryId

) {}
