package com.grocery.inventoryservice.service;

import com.grocery.inventoryservice.dto.InventoryRequestDto;
import com.grocery.inventoryservice.dto.InventoryResponseDto;

import java.util.List;

public interface InventoryService {
    InventoryResponseDto createInventory(InventoryRequestDto request);
    InventoryResponseDto getInventoryByProductId(Long productId);
    List<InventoryResponseDto> getAllInventory();
    InventoryResponseDto updateInventory(Long productId,InventoryRequestDto request);
    void deleteInventory(Long productId);
    boolean checkStock(Long productId,Integer quantity);
    InventoryResponseDto decreaseStock(Long productId, Integer quantity);

}
