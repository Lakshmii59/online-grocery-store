package com.grocery.orderservice.service;

import com.grocery.orderservice.dto.CustomerDto;
import com.grocery.orderservice.dto.CustomerRegisterRequestDto;
import com.grocery.orderservice.dto.LoginRequestDto;

import java.util.List;

public interface CustomerService {
    CustomerDto createCustomer (CustomerRegisterRequestDto customerRegisterRequestDto);
    CustomerDto login(LoginRequestDto request);
    CustomerDto getCustomerById(Long customerId);
    List<CustomerDto> getAllCustomers();
    CustomerDto updateCustomer(Long customerId,CustomerDto customerDto);
    void deleteCustomer(Long customerId);
}
