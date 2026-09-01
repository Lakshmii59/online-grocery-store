package com.grocery.inventoryservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(
        name = "Inventory",
        description = "Inventory details"
)
public record InventoryDto (

     Long inventoryId,

    @NotNull(message = "Product Id is required")
     Long productId,

    @NotNull(message = "Available quantity is required")
    @Min(value = 0,message = "Available quantity cannot be negative")
     Integer availableQuantity,

    @NotNull(message = "Reserved quantity is required")
    @Min(value = 0,message = "Reserved quantity cannot be negative")
     Integer reservedQuantity
){}
