package com.grocery.inventoryservice.service;

import com.grocery.commonlibrary.exception.InventoryAlreadyExistsException;
import com.grocery.inventoryservice.dto.InventoryRequestDto;
import com.grocery.inventoryservice.dto.InventoryResponseDto;
import com.grocery.inventoryservice.entity.Inventory;
import com.grocery.inventoryservice.repository.InventoryRepo;
import com.grocery.inventoryservice.repository.StockMovementRepo;
import com.grocery.inventoryservice.service.impl.InventoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceImplTest {

    @Mock
    private InventoryRepo inventoryRepo;

    @Mock
    private StockMovementRepo stockMovementRepo;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    @Test
    void createInventorySuccess() {

        InventoryRequestDto request = new InventoryRequestDto(
                1L,
                100,
                0
        );

        Inventory inventory = Inventory.builder()
                .inventoryId(1L)
                .productId(1L)
                .availableQuantity(100)
                .reservedQuantity(0)
                .build();

        when(inventoryRepo.findByProductId(1L)).thenReturn(Optional.empty());
        when(inventoryRepo.save(any(Inventory.class))).thenReturn(inventory);

        InventoryResponseDto result = inventoryService.createInventory(request);

        assertNotNull(result);
        assertEquals(1L, result.productId());
        assertEquals(100, result.availableQuantity());

        verify(inventoryRepo).save(any(Inventory.class));
        verify(stockMovementRepo).save(any());
    }

    @Test
    void createInventoryAlreadyExists() {

        Inventory inventory = Inventory.builder()
                .inventoryId(1L)
                .productId(1L)
                .availableQuantity(50)
                .reservedQuantity(0)
                .build();

        when(inventoryRepo.findByProductId(1L))
                .thenReturn(Optional.of(inventory));

        InventoryRequestDto request = new InventoryRequestDto(
                1L,
                100,
                0
        );

        assertThrows(
                InventoryAlreadyExistsException.class,
                () -> inventoryService.createInventory(request)
        );

        verify(inventoryRepo, never()).save(any());

    }

    @Test
    void getInventoryByProductIdSuccess() {

        Inventory inventory = Inventory.builder()
                .inventoryId(1L)
                .productId(1L)
                .availableQuantity(80)
                .reservedQuantity(10)
                .build();

        when(inventoryRepo.findByProductId(1L))
                .thenReturn(Optional.of(inventory));

        InventoryResponseDto dto =
                inventoryService.getInventoryByProductId(1L);

        assertEquals(1L, dto.productId());
        assertEquals(80, dto.availableQuantity());
        assertEquals(10, dto.reservedQuantity());

    }

    @Test
    void getAllInventorySuccess() {

        Inventory inventory = Inventory.builder()
                .inventoryId(1L)
                .productId(1L)
                .availableQuantity(100)
                .reservedQuantity(0)
                .build();

        when(inventoryRepo.findAll())
                .thenReturn(List.of(inventory));

        List<InventoryResponseDto> list =
                inventoryService.getAllInventory();

        assertEquals(1, list.size());
        assertEquals(1L, list.get(0).productId());

    }

    @Test
    void updateInventorySuccess() {

        Inventory inventory = Inventory.builder()
                .inventoryId(1L)
                .productId(1L)
                .availableQuantity(100)
                .reservedQuantity(0)
                .build();

        InventoryRequestDto request = new InventoryRequestDto(
                1L,
                100,
                0
        );

        when(inventoryRepo.findByProductId(1L))
                .thenReturn(Optional.of(inventory));

        when(inventoryRepo.save(any()))
                .thenReturn(inventory);

        InventoryResponseDto result =
                inventoryService.updateInventory(1L, request);

        assertNotNull(result);

        verify(inventoryRepo).save(any());

    }

    @Test
    void reserveInventorySuccess() {

        Inventory inventory = Inventory.builder()
                .inventoryId(1L)
                .productId(1L)
                .availableQuantity(100)
                .reservedQuantity(0)
                .build();

        when(inventoryRepo.findByProductId(1L))
                .thenReturn(Optional.of(inventory));

        when(inventoryRepo.save(any()))
                .thenReturn(inventory);

        InventoryResponseDto dto =
                inventoryService.reserveInventory(1L, 20);

        assertNotNull(dto);

        verify(stockMovementRepo).save(any());

    }

    @Test
    void deleteInventorySuccess() {

        Inventory inventory = Inventory.builder()
                .inventoryId(1L)
                .productId(1L)
                .build();

        when(inventoryRepo.findByProductId(1L)).thenReturn(Optional.of(inventory));

        inventoryService.deleteInventory(1L);

        verify(inventoryRepo).delete(inventory);
    }

    @Test
    void checkStockTrue() {

        Inventory inventory = Inventory.builder()
                .productId(1L)
                .availableQuantity(100)
                .reservedQuantity(0)
                .build();

        when(inventoryRepo.findByProductId(1L)).thenReturn(Optional.of(inventory));

        assertTrue(inventoryService.checkStock(1L, 50));
    }

    @Test
    void checkStockFalse() {

        Inventory inventory = Inventory.builder()
                .productId(1L)
                .availableQuantity(10)
                .reservedQuantity(0)
                .build();

        when(inventoryRepo.findByProductId(1L)).thenReturn(Optional.of(inventory));

        assertFalse(inventoryService.checkStock(1L, 50));
    }
}

