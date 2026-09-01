package com.grocery.orderservice.controller;

import com.grocery.orderservice.dto.CustomerDto;
import com.grocery.orderservice.dto.CustomerRegisterRequestDto;
import com.grocery.orderservice.dto.LoginRequestDto;
import com.grocery.orderservice.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(
        name = "Customer Management",
        description = "REST APIs for managing grocery store customers."
)
public class CustomerController {

    private final CustomerService customerService;

    @Operation(summary = "Create Customer", description = "Creates a new customer.")
    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(
//            @io.swagger.v3.oas.annotations.parameters.RequestBody(
//                    required = true,
//                    content = @Content(
//                            schema = @Schema(implementation = CustomerDto.class),
//                            examples = @ExampleObject(
//                                    value = "{\"customerName\":\"Lakshmi\",\"email\":\"lakshmi@gmail.com\",\"phone\":\"9876543210\"}"
//                            )
//                    )
//            )
            @Valid @RequestBody CustomerRegisterRequestDto request) {
        return new ResponseEntity<>(customerService.createCustomer(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Get Customer By Id", description = "Fetch customer by id.")
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("customerId") Long customerId) {
        return ResponseEntity.ok(customerService.getCustomerById(customerId));
    }

    @Operation(summary = "Get All Customers", description = "Returns all customers.")
    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @Operation(summary = "Update Customer", description = "Updates an existing customer.")
    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerDto> updateCustomer(
            @PathVariable("customerId") Long customerId,
//            @io.swagger.v3.oas.annotations.parameters.RequestBody(
//                    required = true,
//                    content = @Content(
//                            schema = @Schema(implementation = CustomerDto.class),
//                            examples = @ExampleObject(
//                                    value = """
//                                {
//                                  "customerName": "Lakshmi",
//                                  "email": "lakshmi123@gmail.com",
//                                  "phone": "9876543210"
//                                }
//                                """
//                            )
//                    )
//            )
            @Valid @RequestBody CustomerDto customerDto) {

        return ResponseEntity.ok(customerService.updateCustomer(customerId, customerDto));
    }

    @PostMapping("/login")
    public ResponseEntity<CustomerDto> login(
            @Valid @RequestBody LoginRequestDto request) {

        return ResponseEntity.ok(
                customerService.login(request)
        );
    }

    @Operation(summary = "Delete Customer", description = "Deletes a customer.")
    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("customerId") Long customerId) {

        customerService.deleteCustomer(customerId);
        return ResponseEntity.ok("Customer deleted successfully");
    }
}
