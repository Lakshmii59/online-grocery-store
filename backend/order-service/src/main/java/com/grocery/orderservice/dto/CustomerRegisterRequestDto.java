package com.grocery.orderservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CustomerRegisterRequestDto(

        @NotBlank(message = "Customer name is required")
        String customerName,

        @NotBlank(message = "Email is required")
        @Email(message = "Enter a valid email address")
        String email,

        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "^[6-9]\\d{9}$", message = "Enter a valid 10-digit phone number")
        String phone,

        @NotBlank(message = "Password is required")
        @Size(min=8, message = "Password must contain at least 8 characters")
        String password
) {
}
