package com.grocery.orderservice.mapper;

import com.grocery.orderservice.dto.CustomerDto;
import com.grocery.orderservice.dto.CustomerRegisterRequestDto;
import com.grocery.orderservice.entity.Customer;

public class CustomerMapper {
    private CustomerMapper(){}

    public static CustomerDto toDto(Customer customer){
        return new CustomerDto(
                customer.getCustomerId(),
                customer.getCustomerName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getRole());
    }

    public static Customer toEntity(CustomerRegisterRequestDto dto){
        return Customer.builder()
                .customerName(dto.customerName())
                .email(dto.email())
                .phone(dto.phone())
                .password(dto.password())
                .role("CUSTOMER")
                .build();
    }
}
