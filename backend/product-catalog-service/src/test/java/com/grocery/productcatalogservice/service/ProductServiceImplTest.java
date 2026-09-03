package com.grocery.productcatalogservice.service;

import com.grocery.commonlibrary.exception.CategoryNotFoundException;
import com.grocery.commonlibrary.exception.ProductNotFoundException;
import com.grocery.productcatalogservice.dto.ProductRequestDto;
import com.grocery.productcatalogservice.dto.ProductResponseDto;
import com.grocery.productcatalogservice.entity.Category;
import com.grocery.productcatalogservice.entity.Product;
import com.grocery.productcatalogservice.repository.CategoryRepo;
import com.grocery.productcatalogservice.repository.ProductRepo;
import com.grocery.productcatalogservice.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepo productRepo;

    @Mock
    private CategoryRepo categoryRepo;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void getAllProductsSuccess() {

        Category category = Category.builder()
                .categoryId(1L)
                .categoryName("Fruits")
                .build();

        Product product = Product.builder()
                .productId(1L)
                .productName("Apple")
                .sku("APL001")
                .category(category)
                .price(new BigDecimal("100"))
                .availableQuantity(25)
                .active(true)
                .build();

        when(productRepo.findAll()).thenReturn(List.of(product));

        List<ProductResponseDto> result = productService.getAllProducts();

        assertEquals(1, result.size());

    }

    @Test
    void getProductByIdSuccess() {

        Category category = Category.builder()
                .categoryId(1L)
                .categoryName("Fruits")
                .description("Fresh Fruits")
                .build();

        Product product = Product.builder()
                .productId(1L)
                .productName("Apple")
                .sku("APL001")
                .category(category)
                .price(new BigDecimal("100"))
                .availableQuantity(25)
                .active(true)
                .build();

        when(productRepo.findById(1L))
                .thenReturn(Optional.of(product));

        ProductResponseDto dto = productService.getProductById(1L);

        assertNotNull(dto);
        assertEquals("Apple", dto.productName());
    }

    @Test
    void createProductSuccess() {

        Category category = Category.builder()
                .categoryId(1L)
                .categoryName("Fruits")
                .build();

        ProductRequestDto request = new ProductRequestDto(
                "Apple",
                "APL001",
                new BigDecimal("120"),
                20,
                true,
                "/images/apple.jpg",
                1L
        );

        Product product = Product.builder()
                .productId(1L)
                .productName("Apple")
                .sku("APL001")
                .category(category)
                .price(new BigDecimal("120"))
                .availableQuantity(20)
                .active(true)
                .build();

        when(productRepo.findBySku("APL001"))
                .thenReturn(Optional.empty());

        when(categoryRepo.findById(1L))
                .thenReturn(Optional.of(category));

        when(productRepo.save(any(Product.class)))
                .thenReturn(product);

        ProductResponseDto result = productService.createProduct(request);

        assertNotNull(result);
        assertEquals("Apple", result.productName());

        verify(productRepo).save(any(Product.class));
    }

    @Test
    void updateProductSuccess() {

        Category category = Category.builder()
                .categoryId(1L)
                .categoryName("Fruits")
                .build();

        Product product = Product.builder()
                .productId(1L)
                .productName("Apple")
                .sku("APL001")
                .category(category)
                .price(new BigDecimal("100"))
                .availableQuantity(25)
                .active(true)
                .build();

        ProductRequestDto request = new ProductRequestDto(
                "Apple",
                "APL001",
                new BigDecimal("120"),
                20,
                true,
                "/images/apple.jpg",
                1L
        );

        when(categoryRepo.findById(1L))
                .thenReturn(Optional.of(category));

        when(productRepo.findById(1L))
                .thenReturn(Optional.of(product));

        when(productRepo.save(any(Product.class)))
                .thenReturn(product);

        ProductResponseDto result = productService.updateProduct(1L, request);

        assertNotNull(result);
        verify(productRepo).save(any(Product.class));
    }

    @Test
    void updateProductNotFound() {

        when(productRepo.findById(1L))
                .thenReturn(Optional.empty());

        ProductRequestDto request = new ProductRequestDto(
                "Apple",
                "APL001",
                new BigDecimal("120"),
                20,
                true,
                "/images/apple.jpg",
                1L
        );

        assertThrows(ProductNotFoundException.class,
                () -> productService.updateProduct(1L, request));
    }

    @Test
    void createProductCategoryNotFound() {

        ProductRequestDto request = new ProductRequestDto(
                "Apple",
                "APL001",
                new BigDecimal("120"),
                20,
                true,
                "/images/apple.jpg",
                1L
        );

        when(productRepo.findBySku("APL001"))
                .thenReturn(Optional.empty());

        when(categoryRepo.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class,
                () -> productService.createProduct(request));
    }

}
