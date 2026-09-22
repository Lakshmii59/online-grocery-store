package com.grocery.orderservice.service.impl;

import com.grocery.commonlibrary.constants.OrderStatus;
import com.grocery.commonlibrary.exception.*;
import com.grocery.orderservice.client.InventoryClient;
import com.grocery.orderservice.client.ProductClient;
import com.grocery.orderservice.constants.OrderConstants;
import com.grocery.orderservice.dto.*;
import com.grocery.orderservice.entity.Customer;
import com.grocery.orderservice.entity.GroceryOrder;
import com.grocery.orderservice.entity.OrderItem;
import com.grocery.orderservice.mapper.OrderMapper;
import com.grocery.orderservice.repository.CustomerRepo;
import com.grocery.orderservice.repository.GroceryOrderRepo;
import com.grocery.orderservice.repository.OrderItemRepo;
import com.grocery.orderservice.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final GroceryOrderRepo groceryOrderRepo;
    private final OrderItemRepo orderItemRepo;
    private final CustomerRepo customerRepo;
    private final ProductClient productClient;
    private final InventoryClient inventoryClient;

    @Override
    public OrderResponseDto createOrder(OrderRequestDto requestDto) {

        Customer customer = customerRepo.findById(requestDto.customerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id : "
                                + requestDto.customerId()));

        GroceryOrder order = new GroceryOrder();
        order.setCustomer(customer);
        order.setOrderStatus("CREATED");
        order.setOrderDate(LocalDateTime.now(ZoneOffset.UTC));

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemDto itemDto : requestDto.orderItems()) {

            ProductDto product = productClient.getProductById(itemDto.productId());

            if(!Boolean.TRUE.equals(product.active())){
                throw new ProductInactiveException("Product '" + product.productName() + "' is inactive and cannot be ordered.");
            }

            inventoryClient.decreaseStock(
                    itemDto.productId(),
                    itemDto.quantity());

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.productId());
            orderItem.setProductName(product.productName());
            orderItem.setProductPrice(product.price());
            orderItem.setQuantity(itemDto.quantity());

            BigDecimal itemTotal = product.price()
                    .multiply(BigDecimal.valueOf(itemDto.quantity()));

            orderItem.setTotalPrice(itemTotal);
            orderItem.setGroceryOrder(order);

            orderItems.add(orderItem);
            totalAmount = totalAmount.add(itemTotal);
        }

        order.setTotalAmount(totalAmount);
        order.setOrderItems(orderItems);

        GroceryOrder savedOrder = groceryOrderRepo.save(order);

        return OrderMapper.toDto(savedOrder);
    }

    @Override
    public OrderResponseDto getOrderById(Long orderId) {
        GroceryOrder order = groceryOrderRepo.findById(orderId)
                .orElseThrow(()-> new OrderNotFoundException(
                       OrderConstants.ORDER_NOT_FOUND + orderId));
        return OrderMapper.toDto(order);
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {
        return groceryOrderRepo.findAll()
                .stream()
                .map(OrderMapper::toDto)
                .toList();
    }

    @Override
    public OrderResponseDto updateOrder(Long orderId, OrderRequestDto requestDto) {
        GroceryOrder order = groceryOrderRepo.findById(orderId)
                .orElseThrow(()-> new OrderNotFoundException(
                        OrderConstants.ORDER_NOT_FOUND + orderId));

        Customer customer = customerRepo.findById(requestDto.customerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id : " + requestDto.customerId()));
        order.setCustomer(customer);
        GroceryOrder updatedOrder = groceryOrderRepo.save(order);
        return OrderMapper.toDto(updatedOrder);

    }

    @Override
    public OrderResponseDto updateOrderStatus(Long orderId, String status) {
        GroceryOrder order = groceryOrderRepo.findById(orderId)
                .orElseThrow(()-> new OrderNotFoundException(
                        OrderConstants.ORDER_NOT_FOUND + orderId));
        if(OrderStatus.CANCELLED.name().equals(order.getOrderStatus())
        && OrderStatus.DELIVERED.name().equalsIgnoreCase(status)){
            throw new InvalidOrderStatusException("Cancelled orders cannot be delivered");
        }
        order.setOrderStatus(status);
        GroceryOrder updatedOrder = groceryOrderRepo.save(order);
        return OrderMapper.toDto(updatedOrder);
    }

    @Override
    public void deleteOrder(Long orderId) {
        GroceryOrder order = groceryOrderRepo.findById(orderId)
                .orElseThrow(()-> new OrderNotFoundException(
                        OrderConstants.ORDER_NOT_FOUND + orderId));
        groceryOrderRepo.delete(order);
    }

    @Override
    public void cancelOrder(Long orderId) {
        GroceryOrder order = groceryOrderRepo.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(
                        OrderConstants.ORDER_NOT_FOUND + orderId));


        if("DELIVERED".equals(order.getOrderStatus())) {
            throw new InvalidOrderStatusException("Delivered orders cannot be cancelled");
        }

        order.setOrderStatus("CANCELLED");
        groceryOrderRepo.save(order);
    }
}
