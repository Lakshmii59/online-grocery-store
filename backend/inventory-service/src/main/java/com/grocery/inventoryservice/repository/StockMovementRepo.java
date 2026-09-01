package com.grocery.inventoryservice.repository;

import com.grocery.inventoryservice.entity.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockMovementRepo extends JpaRepository<StockMovement, Long> {
}
