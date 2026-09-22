package com.grocery.inventoryservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record InventoryDto (

     Long inventoryId,

    @NotNull(message = "Product Id is required")
     Long productId,

    @NotNull(message = "Available quantity is required")
    @Min(value = 0,message = "Available quantity cannot be negative")
     Integer availableQuantity
){}
