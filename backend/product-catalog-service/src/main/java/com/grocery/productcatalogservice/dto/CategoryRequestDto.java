package com.grocery.productcatalogservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDto(

        @NotBlank(message = "Category name is required")
        @Schema(description = "Category Name", example = "Fruits")
        String categoryName,

        @Schema(description = "Category Description", example = "Fresh fruits and vegetables")
        String description

) {

}
