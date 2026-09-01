package com.grocery.orderservice.repository;

import com.grocery.orderservice.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class CustomerRepoTest {

    @Autowired
    private CustomerRepo customerRepo;

    @Test
    void saveCustomerSuccess() {

        Customer customer = Customer.builder()
                .customerName("Lakshmi")
                .email("lakshmi@gmail.com")
                .phone("9876543210")
                .build();

        Customer saved = customerRepo.save(customer);

        assertNotNull(saved.getCustomerId());
        assertEquals("Lakshmi", saved.getCustomerName());
    }

    @Test
    void findCustomerByIdSuccess() {

        Customer customer = customerRepo.save(
                Customer.builder()
                        .customerName("Lakshmi")
                        .email("lakshmi@gmail.com")
                        .phone("9876543210")
                        .build());

        Optional<Customer> result =
                customerRepo.findById(customer.getCustomerId());

        assertTrue(result.isPresent());
        assertEquals("Lakshmi", result.get().getCustomerName());
    }

    @Test
    void deleteCustomerSuccess() {

        Customer customer = customerRepo.save(
                Customer.builder()
                        .customerName("Lakshmi")
                        .email("lakshmi@gmail.com")
                        .phone("9876543210")
                        .build());

        customerRepo.deleteById(customer.getCustomerId());

        assertFalse(customerRepo.findById(customer.getCustomerId()).isPresent());
    }
}