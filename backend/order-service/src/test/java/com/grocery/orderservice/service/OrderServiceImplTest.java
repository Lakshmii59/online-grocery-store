package com.grocery.orderservice.service;

import com.grocery.commonlibrary.exception.InvalidOrderStatusException;
import com.grocery.commonlibrary.exception.OrderNotFoundException;
import com.grocery.orderservice.client.InventoryClient;
import com.grocery.orderservice.client.ProductClient;
import com.grocery.orderservice.dto.OrderResponseDto;
import com.grocery.orderservice.entity.Customer;
import com.grocery.orderservice.entity.GroceryOrder;
import com.grocery.orderservice.entity.OrderItem;
import com.grocery.orderservice.repository.CustomerRepo;
import com.grocery.orderservice.repository.GroceryOrderRepo;
import com.grocery.orderservice.repository.OrderItemRepo;
import com.grocery.orderservice.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private GroceryOrderRepo groceryOrderRepo;

    @Mock
    private OrderItemRepo orderItemRepo;

    @Mock
    private CustomerRepo customerRepo;

    @Mock
    private ProductClient productClient;

    @Mock
    private InventoryClient inventoryClient;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void getOrderByIdSuccess() {

        Customer customer = Customer.builder()
                .customerId(1L)
                .customerName("Lakshmi")
                .build();

        GroceryOrder order = GroceryOrder.builder()
                .orderId(1L)
                .customer(customer)
                .orderStatus("CREATED")
                .totalAmount(BigDecimal.ZERO)
                .orderItems(new ArrayList<>())
                .build();

        when(groceryOrderRepo.findById(1L))
                .thenReturn(Optional.of(order));

        OrderResponseDto dto = orderService.getOrderById(1L);

        assertNotNull(dto);
        assertEquals(1L, dto.orderId());
    }

    @Test
    void getOrderByIdNotFound() {

        when(groceryOrderRepo.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class,
                () -> orderService.getOrderById(1L));
    }

    @Test
    void getAllOrdersSuccess() {

        Customer customer = Customer.builder()
                .customerId(1L)
                .customerName("Lakshmi")
                .build();

        GroceryOrder order = GroceryOrder.builder()
                .orderId(1L)
                .customer(customer)
                .orderStatus("CREATED")
                .totalAmount(BigDecimal.ZERO)
                .orderItems(new ArrayList<>())
                .build();

        when(groceryOrderRepo.findAll())
                .thenReturn(List.of(order));

        List<OrderResponseDto> list = orderService.getAllOrders();

        assertEquals(1, list.size());
    }

    @Test
    void deleteOrderSuccess() {

        GroceryOrder order = GroceryOrder.builder()
                .orderId(1L)
                .build();

        when(groceryOrderRepo.findById(1L))
                .thenReturn(Optional.of(order));

        orderService.deleteOrder(1L);

        verify(groceryOrderRepo).delete(order);
    }

    @Test
    void deleteOrderNotFound() {

        when(groceryOrderRepo.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class,
                () -> orderService.deleteOrder(1L));
    }

    @Test
    void updateOrderStatusSuccess() {

        Customer customer = Customer.builder()
                .customerId(1L)
                .customerName("Lakshmi")
                .build();

        GroceryOrder order = GroceryOrder.builder()
                .orderId(1L)
                .customer(customer)
                .orderStatus("CREATED")
                .totalAmount(BigDecimal.ZERO)
                .orderItems(new ArrayList<>())
                .build();

        when(groceryOrderRepo.findById(1L))
                .thenReturn(Optional.of(order));

        when(groceryOrderRepo.save(any()))
                .thenReturn(order);

        OrderResponseDto dto =
                orderService.updateOrderStatus(1L, "DELIVERED");

        assertNotNull(dto);

        verify(groceryOrderRepo).save(order);
    }


    @Test
    void updateOrderStatusNotFound() {

        when(groceryOrderRepo.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class,
                () -> orderService.updateOrderStatus(1L, "CONFIRMED"));
    }

    @Test
    void shouldNotCancelDeliveredOrder() {

        GroceryOrder order = new GroceryOrder();
        order.setOrderId(1L);
        order.setOrderStatus("DELIVERED");

        when(groceryOrderRepo.findById(1L))
                .thenReturn(Optional.of(order));

        assertThrows(InvalidOrderStatusException.class,
                () -> orderService.cancelOrder(1L));

        verify(groceryOrderRepo, never()).save(any());
    }


    @Test
    void shouldNotDeliverCancelledOrder() {

        GroceryOrder order = new GroceryOrder();
        order.setOrderId(1L);
        order.setOrderStatus("CANCELLED");

        when(groceryOrderRepo.findById(1L))
                .thenReturn(Optional.of(order));

        assertThrows(InvalidOrderStatusException.class,
                () -> orderService.updateOrderStatus(1L, "DELIVERED"));

        verify(groceryOrderRepo, never()).save(any());
    }

    @Test
    void cancelOrderSuccess() {

        Customer customer = Customer.builder()
                .customerId(1L)
                .customerName("Lakshmi")
                .build();

        OrderItem item = OrderItem.builder()
                .productId(100L)
                .quantity(2)
                .build();

        GroceryOrder order = GroceryOrder.builder()
                .orderId(1L)
                .customer(customer)
                .orderStatus("CREATED")
                .orderItems(List.of(item))
                .build();

        when(groceryOrderRepo.findById(1L))
                .thenReturn(Optional.of(order));

        orderService.cancelOrder(1L);

        assertEquals("CANCELLED", order.getOrderStatus());

        verify(groceryOrderRepo).save(order);

        verifyNoInteractions(inventoryClient);
    }


    @Test
    void cancelOrderNotFound() {

        when(groceryOrderRepo.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class,
                () -> orderService.cancelOrder(1L));
    }
}
