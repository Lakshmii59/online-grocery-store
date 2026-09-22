package com.grocery.inventoryservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record InventoryRequestDto(

        @NotNull(message = "Product Id is required")
        @Schema(example = "1")
        Long productId,

        @NotNull(message = "Available quantity is required")
        @Min(value = 0, message = "Available quantity cannot be negative")
        @Schema(example = "100")
        Integer availableQuantity

) {}
