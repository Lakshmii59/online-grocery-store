package com.grocery.orderservice.controller;

import com.grocery.orderservice.dto.OrderRequestDto;
import com.grocery.orderservice.dto.OrderResponseDto;
import com.grocery.orderservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(
        name = "Order Management",
        description = "REST APIs for managing grocery orders."
)
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Create Order", description = "Creates a new grocery order.")
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(
            @Parameter(description = "Order Id", example = "1")
            @Valid @RequestBody OrderRequestDto requestDto) {
        return new ResponseEntity<>(orderService.createOrder(requestDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Get Order By Id", description = "Fetch order by id.")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable("orderId") Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @Operation(summary = "Get All Orders", description = "Returns all grocery orders.")
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @Operation(summary = "Update Order", description = "Updates an existing order.")
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> updateOrder(
            @Parameter(description = "Order ID",example = "1")
            @PathVariable("orderId") Long orderId,
            @Valid @RequestBody OrderRequestDto requestDto) {

        return ResponseEntity.ok(orderService.updateOrder(orderId, requestDto));
    }

    @Operation(summary = "Update Order Status", description = "Updates the status of an order.")
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderResponseDto> updateOrderStatus(
            @PathVariable("orderId") Long orderId,
            @RequestParam("status") String status) {

        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
    }

    @Operation(summary = "Cancel Order", description = "Cancels an existing order.")
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable("orderId") Long orderId) {

        orderService.cancelOrder(orderId);
        return ResponseEntity.ok("Order cancelled successfully");
    }

    @Operation(summary = "Delete Order", description = "Deletes an order.")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable("orderId") Long orderId) {

        orderService.deleteOrder(orderId);
        return ResponseEntity.ok("Order deleted successfully");
    }
}
