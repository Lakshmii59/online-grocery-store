package com.grocery.orderservice.repository;

import com.grocery.orderservice.entity.Customer;
import com.grocery.orderservice.entity.GroceryOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class GroceryOrderRepoTest {

    @Autowired
    private GroceryOrderRepo groceryOrderRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Test
    void saveOrderSuccess() {

        Customer customer = Customer.builder()
                .customerName("Lakshmi")
                .email("lakshmi@test.com")
                .phone("9876543210")
                .build();

        customer = customerRepo.save(customer);

        GroceryOrder order = GroceryOrder.builder()
                .customer(customer)
                .orderDate(LocalDateTime.now())
                .orderStatus("CREATED")
                .totalAmount(new BigDecimal("200"))
                .orderItems(new ArrayList<>())
                .build();

        GroceryOrder saved =
                groceryOrderRepo.save(order);

        assertNotNull(saved.getOrderId());
    }

    @Test
    void findByIdSuccess() {

        Customer customer = Customer.builder()
                .customerName("Lakshmi")
                .email("lakshmi@test.com")
                .phone("9876543210")
                .build();

        customer = customerRepo.save(customer);

        GroceryOrder order = GroceryOrder.builder()
                .customer(customer)
                .orderDate(LocalDateTime.now())
                .orderStatus("CREATED")
                .totalAmount(new BigDecimal("200"))
                .orderItems(new ArrayList<>())
                .build();

        GroceryOrder saved =
                groceryOrderRepo.save(order);

        Optional<GroceryOrder> result =
                groceryOrderRepo.findById(saved.getOrderId());

        assertTrue(result.isPresent());
    }
}
