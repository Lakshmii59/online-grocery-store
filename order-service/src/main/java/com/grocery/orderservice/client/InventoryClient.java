package com.grocery.orderservice.client;

import com.grocery.orderservice.dto.InventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-service")
public interface InventoryClient {

    @GetMapping("/api/inventory/{productId}")
    InventoryDto getInventory(@PathVariable("productId") Long productId);

    @PutMapping("/api/inventory/{productId}/reserve")
    InventoryDto reserveInventory(
            @PathVariable("productId") Long productId,
            @RequestParam("quantity") Integer quantity);

    @PutMapping("/api/inventory/{productId}/release")
    InventoryDto releaseInventory(
            @PathVariable("productId") Long productId,
            @RequestParam("quantity") Integer quantity);

    @PutMapping("/api/inventory/{productId}/confirm")
    InventoryDto confirmInventory(
            @PathVariable("productId") Long productId,
            @RequestParam("quantity") Integer quantity);
}
