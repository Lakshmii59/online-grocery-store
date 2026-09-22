package com.grocery.orderservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grocery.orderservice.dto.CustomerDto;
import com.grocery.orderservice.dto.CustomerRegisterRequestDto;
import com.grocery.orderservice.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createCustomerSuccess() throws Exception {

        CustomerDto customer = new CustomerDto(
                1L,
                "Lakshmi",
                "lakshmi@gmail.com",
                "9876543210",
                "CUSTOMER"
        );

        when(customerService.createCustomer(any(CustomerRegisterRequestDto.class)))
                .thenReturn(customer);

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.customerName").value("Lakshmi"));
    }

    @Test
    void getCustomerByIdSuccess() throws Exception {

        CustomerDto customer = new CustomerDto(
                1L,
                "Lakshmi",
                "lakshmi@gmail.com",
                "9876543210",
                "CUSTOMER"
        );

        when(customerService.getCustomerById(1L))
                .thenReturn(customer);

        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.customerName").value("Lakshmi"));
    }

    @Test
    void getAllCustomersSuccess() throws Exception {

        CustomerDto customer = new CustomerDto(
                1L,
                "Lakshmi",
                "lakshmi@gmail.com",
                "9876543210",
                "CUSTOMER"
        );

        when(customerService.getAllCustomers())
                .thenReturn(List.of(customer));

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerName").value("Lakshmi"));
    }

    @Test
    void updateCustomerSuccess() throws Exception {

        CustomerDto customer = new CustomerDto(
                1L,
                "Lakshmi",
                "lakshmi123@gmail.com",
                "9876543210",
                "CUSTOMER"
        );

        when(customerService.updateCustomer(eq(1L), any(CustomerDto.class)))
                .thenReturn(customer);

        mockMvc.perform(put("/api/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("Lakshmi"))
                .andExpect(jsonPath("$.email").value("lakshmi123@gmail.com"));
    }

    @Test
    void deleteCustomerSuccess() throws Exception {

        doNothing().when(customerService).deleteCustomer(1L);

        mockMvc.perform(delete("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Customer deleted successfully"));
    }
}
