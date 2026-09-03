package com.grocery.orderservice.repository;

import com.grocery.orderservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
    Optional<Customer>findByEmail(String email);
    Optional<Customer>findByPhone(String phone);
}
