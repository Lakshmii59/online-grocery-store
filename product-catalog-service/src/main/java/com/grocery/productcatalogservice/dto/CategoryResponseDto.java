package com.grocery.productcatalogservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "Category Response",
        description = "Category response details"
)
public record CategoryResponseDto(

        @Schema(description = "Category ID", example = "1")
        Long categoryId,

        @Schema(description = "Category Name", example = "Fruits")
        String categoryName,

        @Schema(description = "Category Description", example = "Fresh fruits and vegetables")
        String description

) {}
