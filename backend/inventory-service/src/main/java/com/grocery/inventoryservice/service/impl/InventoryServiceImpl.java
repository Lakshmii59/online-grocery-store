package com.grocery.inventoryservice.service.impl;

import com.grocery.commonlibrary.exception.InsufficientStockException;
import com.grocery.commonlibrary.exception.InventoryAlreadyExistsException;
import com.grocery.commonlibrary.exception.InventoryNotFoundException;
import com.grocery.inventoryservice.constants.InventoryConstants;
import com.grocery.inventoryservice.dto.InventoryRequestDto;
import com.grocery.inventoryservice.dto.InventoryResponseDto;
import com.grocery.inventoryservice.entity.Inventory;
import com.grocery.inventoryservice.entity.StockMovement;
import com.grocery.inventoryservice.mapper.InventoryMapper;
import com.grocery.inventoryservice.repository.InventoryRepo;
import com.grocery.inventoryservice.repository.StockMovementRepo;
import com.grocery.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepo inventoryRepo;
    private final StockMovementRepo stockMovementRepo;

    @Override
    @Transactional
    public InventoryResponseDto createInventory(InventoryRequestDto request) {

        if (inventoryRepo.findByProductId(request.productId()).isPresent()) {
            throw new InventoryAlreadyExistsException(
                    "Inventory already exists for productId : " + request.productId());
        }

        Inventory inventory = InventoryMapper.toEntity(request);
        Inventory savedInventory = inventoryRepo.save(inventory);

        saveStockMovement(
                savedInventory.getProductId(),
                "CREATE",
                savedInventory.getAvailableQuantity(),
                "Inventory created");

        return InventoryMapper.toResponseDto(savedInventory);
    }

    @Override
    public InventoryResponseDto getInventoryByProductId(Long productId) {

        Inventory inventory = inventoryRepo.findByProductId(productId)
                .orElseThrow(() -> new InventoryNotFoundException(
                        InventoryConstants.INVENTORY_NOT_FOUND_MESSAGE + productId));

        return InventoryMapper.toResponseDto(inventory);
    }

    @Override
    public List<InventoryResponseDto> getAllInventory() {

        return inventoryRepo.findAll()
                .stream()
                .map(InventoryMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public InventoryResponseDto updateInventory(Long productId,
                                                InventoryRequestDto request) {

        Inventory inventory = inventoryRepo.findByProductId(productId)
                .orElseThrow(() -> new InventoryNotFoundException(
                        InventoryConstants.INVENTORY_NOT_FOUND_MESSAGE + productId));

        inventory.setAvailableQuantity(request.availableQuantity());

        Inventory savedInventory = inventoryRepo.save(inventory);

        return InventoryMapper.toResponseDto(savedInventory);
    }

    @Override
    public void deleteInventory(Long productId) {

        Inventory inventory = inventoryRepo.findByProductId(productId)
                .orElseThrow(() -> new InventoryNotFoundException(
                        InventoryConstants.INVENTORY_NOT_FOUND_MESSAGE + productId));

        inventoryRepo.delete(inventory);
    }

    @Override
    public boolean checkStock(Long productId, Integer quantity) {

        validateQuantity(quantity);

        Inventory inventory = inventoryRepo.findByProductId(productId)
                .orElseThrow(() -> new InventoryNotFoundException(
                        InventoryConstants.INVENTORY_NOT_FOUND_MESSAGE + productId));

        return inventory.getAvailableQuantity() >= quantity;
    }

    @Override
    @Transactional
    public InventoryResponseDto decreaseStock(Long productId,
                                              Integer quantity) {

        validateQuantity(quantity);

        Inventory inventory = inventoryRepo.findByProductId(productId)
                .orElseThrow(() -> new InventoryNotFoundException(
                        InventoryConstants.INVENTORY_NOT_FOUND_MESSAGE + productId));

        if (inventory.getAvailableQuantity() < quantity) {
            throw new InsufficientStockException(
                    "Insufficient stock for productId : " + productId);
        }

        inventory.setAvailableQuantity(
                inventory.getAvailableQuantity() - quantity);

        Inventory savedInventory = inventoryRepo.save(inventory);

        saveStockMovement(
                productId,
                "STOCK_DECREASE",
                quantity,
                "Stock decreased for order");

        return InventoryMapper.toResponseDto(savedInventory);
    }

    private void validateQuantity(Integer quantity){
        if(quantity == null || quantity <= 0){
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
    }

    private void saveStockMovement(Long productId,
                                   String movementType,
                                   Integer quantity,
                                   String remarks) {

        StockMovement movement = StockMovement.builder()
                .productId(productId)
                .movementType(movementType)
                .quantity(quantity)
                .movementTime(LocalDateTime.now(ZoneOffset.UTC))
                .remarks(remarks)
                .build();

        stockMovementRepo.save(movement);
    }
}