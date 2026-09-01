package com.grocery.orderservice.dto;

public record InventoryDto (
     Long inventoryId,

     Long productId,

     Integer availableQuantity,

     Integer reservedQuantity
){}
