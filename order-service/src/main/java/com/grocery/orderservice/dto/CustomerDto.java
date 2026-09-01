package com.grocery.orderservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomerDto (
    @Schema(description = "Customer ID",
            example = "1")
     Long customerId,

    @NotBlank(message = "Customer name is required")
    @Schema(description = "Customer name",
    example = "Lakshmi")
     String customerName,

    @Email(message = "Invalid email")
    @Schema(description = "Customer email",
            example = "lakshmi@gmail.com")
     String email,

    @NotBlank(message = "Phone number is required")
    @Schema(description = "Mobile Number",
            example = "9876543212")
     String phone,

    @NotBlank(message = "role should be admin or user")
    String role
){}
