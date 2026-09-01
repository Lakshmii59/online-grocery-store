package com.grocery.productcatalogservice.controller;

import com.grocery.commonlibrary.dto.ErrorResponseDto;
import com.grocery.productcatalogservice.dto.CategoryRequestDto;
import com.grocery.productcatalogservice.dto.CategoryResponseDto;
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
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(
        name = "Category Management",
        description = "REST APIs for managing categories."
)
public class CategoryController {

    private final ProductService productService;

    @Operation(
            summary = "Create Category",
            description = "Creates a new product category."
    )
    @ApiResponses(value ={
            @ApiResponse(responseCode = "201", description = "Category created successfully"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid Request",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(
//            @io.swagger.v3.oas.annotations.parameters.RequestBody(
//                    required = true,
//                    content = @Content(
//                            schema = @Schema(implementation = CategoryRequestDto.class),
//                            examples = @ExampleObject(
//                                    value = """
//                                {
//                                  "categoryName": "Fruits",
//                                  "description": "Fresh fruits category"
//                                }
//                                """
//                            )
//                    )
//            )
            @Valid @RequestBody CategoryRequestDto request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.createCategory(request));
    }

    @Operation(
            summary = "Get All Categories",
            description = "Returns all categories."
    )
    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {

        return ResponseEntity.ok(productService.getAllCategories());
    }

    @Operation(
            summary = "Get Category By Id",
            description = "Returns category by id."
    )
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Category found"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(

            @Parameter(description = "Category Id", example = "1")
            @PathVariable("categoryId") Long categoryId) {

        return ResponseEntity.ok(
                productService.getCategoryById(categoryId));
    }

    @Operation(
            summary = "Update Category",
            description = "Updates the details of an existing product category."
    )
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDto> updateCategory(
            @PathVariable("categoryId") Long categoryId,
//            @io.swagger.v3.oas.annotations.parameters.RequestBody(
//                    required = true,
//                    content = @Content(
//                            schema = @Schema(implementation = CategoryRequestDto.class),
//                            examples = @ExampleObject(
//                                    value = """
//                                {
//                                  "categoryName": "Vegetables",
//                                  "description": "Fresh vegetables category"
//                                }
//                                """
//                            )
//                    )
//            )
            @Valid @RequestBody CategoryRequestDto categoryRequestDto) {

        return ResponseEntity.ok(productService.updateCategory(categoryId, categoryRequestDto));
    }


    @Operation(
            summary = "Delete Category",
            description = "Deletes an existing product category."
    )
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable("categoryId") Long categoryId) {

        productService.deleteCategory(categoryId);

        return ResponseEntity.ok("Category deleted successfully");
    }

}
