package com.grocery.orderservice.service.impl;

import com.grocery.commonlibrary.exception.CustomerAlreadyExistsException;
import com.grocery.commonlibrary.exception.CustomerNotFoundException;
import com.grocery.commonlibrary.exception.InvalidCredentialsException;
import com.grocery.orderservice.constants.OrderConstants;
import com.grocery.orderservice.dto.CustomerDto;
import com.grocery.orderservice.dto.CustomerRegisterRequestDto;
import com.grocery.orderservice.dto.LoginRequestDto;
import com.grocery.orderservice.entity.Customer;
import com.grocery.orderservice.mapper.CustomerMapper;
import com.grocery.orderservice.repository.CustomerRepo;
import com.grocery.orderservice.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;

    @Override
    @Transactional
    public CustomerDto createCustomer(CustomerRegisterRequestDto request) {

        if(customerRepo.findByEmail(request.email()).isPresent()){
            throw new CustomerAlreadyExistsException("Email already exists");
        }
        if(customerRepo.findByPhone(request.phone()).isPresent()){
            throw new CustomerAlreadyExistsException("Phone number already exists");
        }

        Customer customer = CustomerMapper.toEntity(request);
        Customer savedCustomer = customerRepo.save(customer);

        return CustomerMapper.toDto(savedCustomer);
    }

    @Override
    public CustomerDto login(LoginRequestDto request) {

        Customer customer = customerRepo.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!request.password().equals(customer.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        return CustomerMapper.toDto(customer);
    }

    @Override
    public CustomerDto getCustomerById(Long customerId) {

        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(
                        OrderConstants.CUSTOMER_NOT_FOUND + customerId));

        return CustomerMapper.toDto(customer);
    }

    @Override
    public List<CustomerDto> getAllCustomers() {

        return customerRepo.findAll()
                .stream()
                .map(CustomerMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public CustomerDto updateCustomer(Long customerId, CustomerDto customerDto) {

        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(
                                OrderConstants.CUSTOMER_NOT_FOUND + customerId));
        customer.setCustomerName(customerDto.customerName());
        customer.setEmail(customerDto.email());
        customer.setPhone(customerDto.phone());

        Customer updatedCustomer = customerRepo.save(customer);

        return CustomerMapper.toDto(updatedCustomer);
    }

    @Override
    @Transactional
    public void deleteCustomer(Long customerId) {

        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(
                        OrderConstants.CUSTOMER_NOT_FOUND + customerId));

        customerRepo.delete(customer);
    }
}