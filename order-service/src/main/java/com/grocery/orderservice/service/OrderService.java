package com.grocery.orderservice.service;

import com.grocery.orderservice.dto.OrderRequestDto;
import com.grocery.orderservice.dto.OrderResponseDto;

import java.util.List;

public interface OrderService {
    OrderResponseDto createOrder(OrderRequestDto requestDto);
    OrderResponseDto getOrderById(Long orderId);
    List<OrderResponseDto> getAllOrders();
    OrderResponseDto updateOrder(Long orderId,OrderRequestDto requestDto);
    OrderResponseDto updateOrderStatus(Long orderId,String status);
    void deleteOrder(Long orderId);
    void cancelOrder(Long orderId);

    OrderResponseDto confirmOrder(Long orderId);
}
