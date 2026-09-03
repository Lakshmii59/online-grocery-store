package com.grocery.orderservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDto (
     Long orderId,
     Long customerId,
     String customerName,
     BigDecimal totalAmount,
     String orderStatus,
     LocalDateTime orderDate,
     List<OrderItemDto> orderItems
){}
