package com.grocery.inventoryservice.controller;

import com.grocery.commonlibrary.dto.ErrorResponseDto;
import com.grocery.inventoryservice.dto.InventoryRequestDto;
import com.grocery.inventoryservice.dto.InventoryResponseDto;
import com.grocery.inventoryservice.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Tag(
        name = "Inventory Management APIs",
        description = """
                REST APIs for managing grocery store inventory.
 
                Features:
                • Create Inventory
                • Retrieve Inventory
                • Reserve Inventory
                • Release Reserved Inventory
                • Confirm Reserved Inventory
                """
)
public class InventoryController {

    private final InventoryService inventoryService;

    @Operation(
            summary = "Create Product Inventory",
            description = """
                    Creates a new inventory record for a product.
 
                    Business Rules:
                    • Inventory must be unique.
                    • Available quantity cannot be negative.
                    • Reserved quantity cannot be negative.
                    """
    )
    @ApiResponses(value ={
            @ApiResponse(responseCode = "201", description = "Inventory created successfully"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    @PostMapping
    public ResponseEntity<InventoryResponseDto> createInventory(
            @Valid @RequestBody InventoryRequestDto request) {
        return new ResponseEntity<>(
                inventoryService.createInventory(request),
                HttpStatus.CREATED);
    }

    @Operation(
            summary = "Retrieve All Inventory",
            description = "Retrieves all inventory records."
    )
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Inventory retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<InventoryResponseDto>> getAllInventory() {

        return ResponseEntity.ok(
                inventoryService.getAllInventory());
    }

    @Operation(
            summary = "Retrieve Product Inventory",
            description = """
                    Retrieves inventory details of a product including
                    available quantity and reserved quantity.
                    """
    )
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Inventory retrieved successfully"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Inventory not found",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    @GetMapping("/{productId}")
    public ResponseEntity<InventoryResponseDto> getInventoryByProductId(

            @Parameter(
                    description = "Unique Product Identifier",
                    example = "101",
                    required = true
            )

            @PathVariable("productId") Long productId) {

        return ResponseEntity.ok(
                inventoryService.getInventoryByProductId(productId));
    }

    @Operation(
            summary = "Update Product Inventory",
            description = """
                Updates the available and reserved quantity
                of an existing inventory.
                """
    )
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Inventory updated successfully"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Inventory not found",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    @PutMapping("/{productId}")
    public ResponseEntity<InventoryResponseDto> updateInventory(
            @PathVariable("productId") Long productId,
            @Valid @RequestBody InventoryRequestDto request) {

        return ResponseEntity.ok(
                inventoryService.updateInventory(productId, request));
    }

    @Operation(
            summary = "Delete Product Inventory",
            description = "Deletes inventory for a given product."
    )
    @ApiResponses(value ={
            @ApiResponse(responseCode = "204", description = "Inventory deleted successfully"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Inventory not found",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteInventory(

            @Parameter(description = "Product Identifier", example = "101")
            @PathVariable("productId") Long productId) {

        inventoryService.deleteInventory(productId);

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Check Stock Availability",
            description = "Checks whether sufficient stock is available for the given product."
    )
    @ApiResponses( value ={
            @ApiResponse(responseCode = "200", description = "Stock status retrieved successfully"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Inventory not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    @GetMapping("/check/{productId}/{quantity}")
    public ResponseEntity<Boolean> checkStock(

            @Parameter(description = "Product Identifier", example = "201")
            @PathVariable("productId") Long productId,

            @Parameter(description = "Required Quantity", example = "10")
            @PathVariable("quantity") Integer quantity) {

        return ResponseEntity.ok(
                inventoryService.checkStock(productId, quantity));
    }

    @Operation(
            summary = "Decrease Product Stock",
            description = "Decreases available stock after an order is placed."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Stock decreased successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Insufficient stock",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Inventory not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping("/{productId}/decrease")
    public ResponseEntity<InventoryResponseDto> decreaseStock(

            @Parameter(description = "Product Identifier", example = "101")
            @PathVariable("productId") Long productId,

            @Parameter(description = "Quantity to decrease", example = "5")
            @RequestParam("quantity") Integer quantity) {

        return ResponseEntity.ok(
                inventoryService.decreaseStock(productId, quantity));
    }
}