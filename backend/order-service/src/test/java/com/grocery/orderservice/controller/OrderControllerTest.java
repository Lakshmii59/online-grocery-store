package com.grocery.orderservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grocery.orderservice.dto.OrderItemDto;
import com.grocery.orderservice.dto.OrderRequestDto;
import com.grocery.orderservice.dto.OrderResponseDto;
import com.grocery.orderservice.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createOrderSuccess() throws Exception {

        OrderItemDto item = new OrderItemDto(
                null,
                1L,
                null,
                null,
                2,
                null
        );

        OrderRequestDto request = new OrderRequestDto(
          1L,
          List.of(item)
        );

        OrderResponseDto response = new OrderResponseDto(
                1L,
                1L,
                "Lakshmi",
                new BigDecimal("200"),
                "CREATED",
                LocalDateTime.now(),
                List.of()
                );

        when(orderService.createOrder(any(OrderRequestDto.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderId").value(1));
    }

    @Test
    void getOrderByIdSuccess() throws Exception {


        OrderResponseDto response = new OrderResponseDto(
                1L,
                1L,
                "Lakshmi",
                new BigDecimal("200"),
                "CREATED",
                LocalDateTime.now(),
                List.of()
        );

        when(orderService.getOrderById(1L))
                .thenReturn(response);

        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(1));
    }

    @Test
    void getAllOrdersSuccess() throws Exception {


        OrderResponseDto response = new OrderResponseDto(
                1L,
                1L,
                "Lakshmi",
                new BigDecimal("200"),
                "CREATED",
                LocalDateTime.now(),
                List.of()
        );

        when(orderService.getAllOrders())
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderId").value(1));
    }

    @Test
    void updateOrderSuccess() throws Exception {

        OrderItemDto item = new OrderItemDto(
                null,
                1L,
                null,
                null,
                2,
                null
        );

        OrderRequestDto request = new OrderRequestDto(
                1L,
                List.of(item)
        );


        OrderResponseDto response = new OrderResponseDto(
                1L,
                1L,
                "Lakshmi",
                new BigDecimal("200"),
                "UPDATED",
                LocalDateTime.now(),
                List.of()
        );

        when(orderService.updateOrder(any(Long.class), any(OrderRequestDto.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderStatus").value("UPDATED"));
    }

    @Test
    void updateOrderStatusSuccess() throws Exception {


        OrderResponseDto response = new OrderResponseDto(
                1L,
                1L,
                "Lakshmi",
                new BigDecimal("200"),
                "CONFIRMED",
                LocalDateTime.now(),
                List.of()
        );

        when(orderService.updateOrderStatus(1L, "CONFIRMED"))
                .thenReturn(response);

        mockMvc.perform(patch("/api/orders/1/status")
                        .param("status", "CONFIRMED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderStatus").value("CONFIRMED"));
    }

    @Test
    void cancelOrderSuccess() throws Exception {

        doNothing().when(orderService).cancelOrder(1L);

        mockMvc.perform(put("/api/orders/1/cancel"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteOrderSuccess() throws Exception {

        doNothing().when(orderService).deleteOrder(1L);

        mockMvc.perform(delete("/api/orders/1"))
                .andExpect(status().isOk());
    }
}
