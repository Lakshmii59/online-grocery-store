package com.grocery.inventoryservice.mapper;

import com.grocery.inventoryservice.dto.InventoryRequestDto;
import com.grocery.inventoryservice.dto.InventoryResponseDto;
import com.grocery.inventoryservice.entity.Inventory;

public class InventoryMapper {
    private InventoryMapper(){}
    public static InventoryResponseDto toResponseDto(Inventory inventory){
        return new InventoryResponseDto(
                inventory.getInventoryId(),
                inventory.getProductId(),
                inventory.getAvailableQuantity()
        );
    }

    public static Inventory toEntity(InventoryRequestDto request){
        return Inventory.builder()
                .productId(request.productId())
                .availableQuantity(request.availableQuantity())
                .build();
    }
}
