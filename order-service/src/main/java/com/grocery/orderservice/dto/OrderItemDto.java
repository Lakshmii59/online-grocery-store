package com.grocery.orderservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;


public record OrderItemDto (
     Long orderItemId,

    @NotNull(message = "Product Id is required")
     Long productId,

     String productName,
     BigDecimal productPrice,

    @Min(value = 1,message = "Quantity must be greater than zero")
     Integer quantity,
     BigDecimal totalPrice
){}
