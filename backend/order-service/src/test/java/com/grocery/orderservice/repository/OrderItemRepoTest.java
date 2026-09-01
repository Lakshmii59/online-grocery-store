package com.grocery.orderservice.repository;

import com.grocery.orderservice.entity.OrderItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class OrderItemRepoTest {
    @Autowired
    private OrderItemRepo orderItemRepo;

    @Test
    void saveOrderItemSuccess() {

        OrderItem item = OrderItem.builder()
                .productId(1L)
                .productName("Apple")
                .productPrice(new BigDecimal("120.50"))
                .quantity(2)
                .totalPrice(new BigDecimal("241.00"))
                .build();

        OrderItem saved = orderItemRepo.save(item);

        assertNotNull(saved.getOrderItemId());
        assertEquals("Apple", saved.getProductName());
    }

    @Test
    void findOrderItemByIdSuccess() {

        OrderItem saved = orderItemRepo.save(
                OrderItem.builder()
                        .productId(1L)
                        .productName("Apple")
                        .productPrice(new BigDecimal("120.50"))
                        .quantity(2)
                        .totalPrice(new BigDecimal("241.00"))
                        .build());

        Optional<OrderItem> result =
                orderItemRepo.findById(saved.getOrderItemId());

        assertTrue(result.isPresent());
        assertEquals("Apple", result.get().getProductName());
    }

    @Test
    void deleteOrderItemSuccess() {

        OrderItem saved = orderItemRepo.save(
                OrderItem.builder()
                        .productId(1L)
                        .productName("Apple")
                        .productPrice(new BigDecimal("120.50"))
                        .quantity(2)
                        .totalPrice(new BigDecimal("241.00"))
                        .build());

        orderItemRepo.deleteById(saved.getOrderItemId());

        assertFalse(orderItemRepo.findById(saved.getOrderItemId()).isPresent());
    }
}
