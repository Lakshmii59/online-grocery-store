package com.grocery.orderservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grocery.orderservice.client.InventoryClient;
import com.grocery.orderservice.client.ProductClient;
import com.grocery.orderservice.dto.InventoryDto;
import com.grocery.orderservice.dto.OrderItemDto;
import com.grocery.orderservice.dto.OrderRequestDto;
import com.grocery.orderservice.dto.ProductDto;
import com.grocery.orderservice.entity.Customer;
import com.grocery.orderservice.repository.CustomerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductClient productClient;

    @MockitoBean
    private InventoryClient inventoryClient;

    @Autowired
    CustomerRepo customerRepo;

    @BeforeEach
    void setup(){
        if(customerRepo.count() == 0){
            Customer customer = new Customer();
            customer.setCustomerName("Lakshmi");
            customer.setEmail("lakshmi@email.com");
            customer.setPhone("9876543216");

            customerRepo.save(customer);
        }

    }
    @Test
    void shouldCreateOrderSuccessfully() throws Exception {

        ProductDto product = new ProductDto(
                1L,
                "Apple",
                "APL001",
                new BigDecimal("120"),
                20,
                true,
                1L,
                "Fresh fruits"
        );

        InventoryDto inventory = new InventoryDto(
                1L,
                1L,
                20,
                0
        );

        when(productClient.getProductById(1L)).thenReturn(product);
        when(inventoryClient.getInventory(1L)).thenReturn(inventory);
        when(inventoryClient.reserveInventory(eq(1L), anyInt())).thenReturn(inventory);

        OrderItemDto item = new OrderItemDto(
                null,
                1L,
                "Apple",
                new BigDecimal("120"),
                2,
                new BigDecimal("240")
        );

        Long customerId = customerRepo.findAll().get(0).getCustomerId();

        OrderRequestDto request = new OrderRequestDto(
                customerId,
                List.of(item)
        );

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").value(1));
    }

    @Test
    void shouldFailWhenInventoryIsInsufficient() throws Exception {

        ProductDto product = new ProductDto(
                1L,
                "Apple",
                "APL001",
                new BigDecimal("120"),
                20,
                true,
                1L,
                "Fresh fruits"
        );

        InventoryDto inventory = new InventoryDto(
                1L,
                1L,
                1,
                0
        );

        when(productClient.getProductById(1L)).thenReturn(product);
        when(inventoryClient.getInventory(1L)).thenReturn(inventory);

        OrderItemDto item = new OrderItemDto(
                null,
                1L,
                "Apple",
                new BigDecimal("120"),
                2,
                new BigDecimal("240")
        );

        Long customerId = customerRepo.findAll().get(0).getCustomerId();

        OrderRequestDto request = new OrderRequestDto(
                customerId,
                List.of(item)
        );

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldFailWhenProductDoesNotExist() throws Exception {

        when(productClient.getProductById(1L))
                .thenThrow(new RuntimeException("Product not found"));

        OrderItemDto item = new OrderItemDto(
                null,
                1L,
                "Apple",
                new BigDecimal("120"),
                1,
                new BigDecimal("240")
        );

        Long customerId = customerRepo.findAll().get(0).getCustomerId();

        OrderRequestDto request = new OrderRequestDto(
                customerId,
                List.of(item)
        );

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is5xxServerError());
    }
}
