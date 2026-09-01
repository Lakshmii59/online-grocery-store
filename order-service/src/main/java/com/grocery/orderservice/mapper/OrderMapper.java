package com.grocery.orderservice.mapper;

import com.grocery.orderservice.dto.OrderItemDto;
import com.grocery.orderservice.dto.OrderResponseDto;
import com.grocery.orderservice.entity.GroceryOrder;
import com.grocery.orderservice.entity.OrderItem;

import java.util.List;

public class OrderMapper {
    private OrderMapper(){}
    public static OrderResponseDto toDto(GroceryOrder order){
        List<OrderItemDto> items= order.getOrderItems()
                .stream()
                .map(OrderMapper::toOrderItemDto)
                .toList();

        return new OrderResponseDto(
                order.getOrderId(),
                order.getCustomer().getCustomerId(),
                order.getCustomer().getCustomerName(),
                order.getTotalAmount(),
                order.getOrderStatus(),
                order.getOrderDate(),
                items
                );
    }

    public static OrderItemDto toOrderItemDto(OrderItem item){
        return new OrderItemDto(
                item.getOrderItemId(),
                item.getProductId(),
                item.getProductName(),
                item.getProductPrice(),
                item.getQuantity(),
                item.getTotalPrice()
                );
    }
}
