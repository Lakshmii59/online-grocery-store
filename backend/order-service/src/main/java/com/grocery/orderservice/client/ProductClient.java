package com.grocery.orderservice.client;

import com.grocery.orderservice.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-catalog-service")
public interface ProductClient {

    @GetMapping("/api/products/{productId}")
    ProductDto getProductById (@PathVariable("productId") Long productId);
}
