package com.grocery.productcatalogservice.repository;

import com.grocery.productcatalogservice.entity.Category;
import com.grocery.productcatalogservice.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class ProductRepoTest {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Test
    void findBySkuSuccess() {

        Category category = Category.builder()
                .categoryName("Fruits")
                .description("Fresh")
                .build();

        category = categoryRepo.save(category);

        Product product = Product.builder()
                .productName("Apple")
                .sku("APL001")
                .price(new BigDecimal("100"))
                .availableQuantity(20)
                .active(true)
                .category(category)
                .build();

        productRepo.save(product);

        Optional<Product> result =
                productRepo.findBySku("APL001");

        assertTrue(result.isPresent());
        assertEquals("Apple", result.get().getProductName());
    }

    @Test
    void findBySkuNotFound() {

        Optional<Product> result =
                productRepo.findBySku("INVALID");

        assertFalse(result.isPresent());
    }
}