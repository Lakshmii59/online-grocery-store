package com.grocery.orderservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequestDto (
    @NotNull(message = "Customer Id is required")
    Long customerId,

    @Valid
    @NotEmpty(message = "Order must contain atleast one item")
     List<OrderItemDto> orderItems
){}
