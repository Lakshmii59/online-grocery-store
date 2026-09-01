package com.grocery.productcatalogservice.controller;

import com.grocery.commonlibrary.dto.ErrorResponseDto;
import com.grocery.productcatalogservice.dto.ProductRequestDto;
import com.grocery.productcatalogservice.dto.ProductResponseDto;
import com.grocery.productcatalogservice.service.ProductService;
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
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(
        name = "Product Management",
        description = "REST APIs for managing products."
)
public class ProductController {

    private final ProductService productService;

    @Operation(
            summary = "Create Product",
            description = "Creates a new product in the catalog."
    )
    @ApiResponses(value ={
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid Request",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(
            @Parameter(description = "Product ID", example = "1")
//            @io.swagger.v3.oas.annotations.parameters.RequestBody(
//                    required = true,
//                    content = @Content(
//                            schema = @Schema(implementation = ProductRequestDto.class),
//                            examples = @ExampleObject(
//                                    value = """
//                        {
//                          "name": "Apple",
//                          "description": "Fresh Red Apple",
//                          "price": 120.50,
//                          "category": "Fruits"
//                        }
//                        """
//                            )
//                    )
//            )
            @Valid @RequestBody ProductRequestDto request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.createProduct(request));
    }

    @Operation(
            summary = "Get All Products",
            description = "Returns all products."
    )
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {

        return ResponseEntity.ok(productService.getAllProducts());
    }

    @Operation(
            summary = "Get Product By Id",
            description = "Returns product by id."
    )
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> getProductById(

            @Parameter(description = "Product Id", example = "1")
            @PathVariable("productId") Long productId) {

        return ResponseEntity.ok(
                productService.getProductById(productId));
    }

    @Operation(
            summary = "Update Product",
            description = "Updates an existing product."
    )
    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> updateProduct(

            @Parameter(description = "Product Id", example = "1")
            @PathVariable("productId") Long productId,
//            @io.swagger.v3.oas.annotations.parameters.RequestBody(
//                    required = true,
//                    content = @Content(
//                            schema = @Schema(implementation = ProductRequestDto.class),
//                            examples = @ExampleObject(
//                                    value = """
//                        {
//                          "name": "Apple",
//                          "description": "Premium Fresh Apple",
//                          "price": 150.00,
//                          "category": "Fruits"
//                        }
//                        """
//                            )
//                    )
//            )
            @Valid @RequestBody ProductRequestDto request) {

        return ResponseEntity.ok(
                productService.updateProduct(productId, request));
    }

    @Operation(
            summary = "Delete Product",
            description = "Deletes a product."
    )
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(

            @Parameter(description = "Product Id", example = "1")
            @PathVariable("productId") Long productId) {

        productService.deleteProduct(productId);

        return ResponseEntity.ok("Product deleted successfully");
    }
}