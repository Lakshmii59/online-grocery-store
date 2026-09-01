package com.grocery.orderservice.repository;

import com.grocery.orderservice.entity.GroceryOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroceryOrderRepo extends JpaRepository<GroceryOrder, Long> {

}
