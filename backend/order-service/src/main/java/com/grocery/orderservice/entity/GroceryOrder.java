package com.grocery.orderservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "grocery_orders")
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroceryOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private BigDecimal totalAmount;
    private String orderStatus;
    private LocalDateTime orderDate;
    @OneToMany(mappedBy = "groceryOrder",
    cascade = CascadeType.ALL,
    orphanRemoval = true)
    private List<OrderItem> orderItems;
}
