package com.grocery.orderservice.service;

import com.grocery.commonlibrary.exception.CustomerNotFoundException;
import com.grocery.orderservice.dto.CustomerDto;
import com.grocery.orderservice.dto.CustomerRegisterRequestDto;
import com.grocery.orderservice.entity.Customer;
import com.grocery.orderservice.repository.CustomerRepo;
import com.grocery.orderservice.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepo customerRepo;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void createCustomerSuccess() {

        CustomerRegisterRequestDto request = new CustomerRegisterRequestDto(
                "Lakshmi",
                "lakshmi@gmail.com",
                "9876543210",
                "hello"
        );

        Customer savedCustomer = Customer.builder()
                .customerId(1L)
                .customerName("Lakshmi")
                .email("lakshmi@gmail.com")
                .phone("9876543210")
                .password("hello")
                .build();

        when(customerRepo.save(any(Customer.class))).thenReturn(savedCustomer);

        CustomerDto response = customerService.createCustomer(request);

        assertNotNull(response);
        assertEquals(1L, response.customerId());
        assertEquals("Lakshmi", response.customerName());

        verify(customerRepo).save(any(Customer.class));
    }

    @Test
    void getCustomerByIdSuccess() {

        Customer customer = Customer.builder()
                .customerId(1L)
                .customerName("Lakshmi")
                .email("lakshmi@gmail.com")
                .phone("9876543210")
                .build();

        when(customerRepo.findById(1L)).thenReturn(Optional.of(customer));

        CustomerDto response = customerService.getCustomerById(1L);

        assertEquals("Lakshmi", response.customerName());
        assertEquals("lakshmi@gmail.com", response.email());
    }

    @Test
    void getCustomerByIdNotFound() {

        when(customerRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class,
                () -> customerService.getCustomerById(1L));
    }

    @Test
    void getAllCustomersSuccess() {

        Customer customer = Customer.builder()
                .customerId(1L)
                .customerName("Lakshmi")
                .email("lakshmi@gmail.com")
                .phone("9876543210")
                .build();

        when(customerRepo.findAll()).thenReturn(List.of(customer));

        List<CustomerDto> customers = customerService.getAllCustomers();

        assertEquals(1, customers.size());
        assertEquals("Lakshmi", customers.get(0).customerName());
    }

    @Test
    void updateCustomerSuccess() {

        Customer existing = Customer.builder()
                .customerId(1L)
                .customerName("Lakshmi")
                .email("lakshmi@gmail.com")
                .phone("9876543210")
                .build();

        Customer updated = Customer.builder()
                .customerId(1L)
                .customerName("Lakshmi Devi")
                .email("lakshmi123@gmail.com")
                .phone("9999999999")
                .build();

        CustomerDto request = new CustomerDto(
                1L,
                "Lakshmi Devi",
                "lakshmi123@gmail.com",
                "9999999999",
                "customer"
        );

        when(customerRepo.findById(1L)).thenReturn(Optional.of(existing));
        when(customerRepo.save(any(Customer.class))).thenReturn(updated);

        CustomerDto response = customerService.updateCustomer(1L, request);

        assertEquals("Lakshmi Devi", response.customerName());
        assertEquals("lakshmi123@gmail.com", response.email());

        verify(customerRepo).save(any(Customer.class));
    }

    @Test
    void updateCustomerNotFound() {

        CustomerDto request = new CustomerDto(
                1L,
                "Lakshmi",
                "lakshmi@gmail.com",
                "9876543210",
                "customer"
        );

        when(customerRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class,
                () -> customerService.updateCustomer(1L, request));
    }

    @Test
    void deleteCustomerSuccess() {

        Customer customer = Customer.builder()
                .customerId(1L)
                .customerName("Lakshmi")
                .email("lakshmi@gmail.com")
                .phone("9876543210")
                .build();

        when(customerRepo.findById(1L)).thenReturn(Optional.of(customer));
        doNothing().when(customerRepo).delete(customer);

        customerService.deleteCustomer(1L);

        verify(customerRepo).delete(customer);
    }

    @Test
    void deleteCustomerNotFound() {

        when(customerRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class,
                () -> customerService.deleteCustomer(1L));
    }
}
