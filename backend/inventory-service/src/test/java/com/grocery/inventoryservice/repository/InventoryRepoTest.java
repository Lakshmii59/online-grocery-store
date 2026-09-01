package com.grocery.inventoryservice.repository;

import com.grocery.inventoryservice.entity.Inventory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class InventoryRepoTest {

    @Autowired
    private InventoryRepo inventoryRepo;

    @Test
    void findByProductIdSuccess() {

        Inventory inventory = Inventory.builder()
                .productId(101L)
                .availableQuantity(100)
                .reservedQuantity(0)
                .build();

        inventoryRepo.save(inventory);

        Optional<Inventory> result =
                inventoryRepo.findByProductId(101L);

        assertTrue(result.isPresent());
        assertEquals(101L, result.get().getProductId());
    }

    @Test
    void findByProductIdNotFound() {

        Optional<Inventory> result =
                inventoryRepo.findByProductId(999L);

        assertFalse(result.isPresent());
    }
}

