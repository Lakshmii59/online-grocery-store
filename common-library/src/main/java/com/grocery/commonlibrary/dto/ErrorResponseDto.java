package com.grocery.commonlibrary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "ErrorResponse",
        description = "Schema to hold error response information"
)
public class ErrorResponseDto {

    @Schema(
            description = "API path invoked by client",
            example = "/api/inventory/101"
    )
    private String apiPath;

    @Schema(
            description = "Error code",
            example = "PRODUCT_NOT_FOUND"
    )
    private String errorCode;

    @Schema(
            description = "Error message",
            example = "Inventory not found"
    )
    private String message;

    @Schema(
            description = "Time representing when the error happened",
            example = "2026-06-29T15:30:45"
    )
    private LocalDateTime errorTime;
}