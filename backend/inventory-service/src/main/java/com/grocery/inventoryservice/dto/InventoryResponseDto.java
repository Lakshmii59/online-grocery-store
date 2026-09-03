package com.grocery.inventoryservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record InventoryResponseDto(

        @Schema(description = "Inventory Id", example = "1")
        Long inventoryId,

        @Schema(description = "Product Id", example = "1")
        Long productId,

        @Schema(description = "Available Quantity", example = "100")
        Integer availableQuantity,

        @Schema(description = "Reserved Quantity", example = "10")
        Integer reservedQuantity

) {
}
