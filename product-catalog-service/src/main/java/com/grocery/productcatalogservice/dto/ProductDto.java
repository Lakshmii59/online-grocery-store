package com.grocery.productcatalogservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(name = "Product", description = "Product Details")
public record ProductDto (

    @Schema(description = "Product ID", example = "1")
     Long productId,

    @NotBlank(message = "Product name is required")
    @Schema(description = "Product name",example = "Apple")
     String productName,

    @NotBlank(message = "SKU is required")
    @Schema(description = "Product SKU",example = "APL001")
     String sku,

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Schema(description = "Product Price",example = "120.50")
     BigDecimal price,

    @NotNull(message = "Available quantity is required")
    @Min(value = 0, message = "Available quantity cannot be negative")
    @Schema(description = "Available stock",example = "100")
     Integer availableQuantity,

    @NotNull(message = "Active status is required")
    @Schema(description = "Product Status",example = "true")
     Boolean active,

    @NotNull(message = "Category Id is required")
    @Schema(description = "Category ID",example = "1")
     Long categoryId
){}