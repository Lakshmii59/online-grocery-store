package com.grocery.productcatalogservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grocery.productcatalogservice.dto.ProductRequestDto;
import com.grocery.productcatalogservice.dto.ProductResponseDto;
import com.grocery.productcatalogservice.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createProductSuccess() throws Exception {

        ProductRequestDto request = new ProductRequestDto(
                "Apple",
                "APL001",
                new BigDecimal("120"),
                20,
                true,
                "/images/apple.jpg",
                1L
        );

        ProductResponseDto response = new ProductResponseDto(
                1L,
                "Apple",
                "APL001",
                new BigDecimal("120"),
                20,
                true,
                "/images/apple.jpg",
                1L
        );

        when(productService.createProduct(any(ProductRequestDto.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productId").value(1));
    }


    @Test
    void getAllProductsSuccess() throws Exception {

        ProductResponseDto response = new ProductResponseDto(
                1L,
                "Apple",
                "APL001",
                new BigDecimal("120"),
                20,
                true,
                "/images/apple.jpg",
                1L
        );

        when(productService.getAllProducts())
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productName").value("Apple"));
    }


    @Test
    void getProductByIdSuccess() throws Exception {

        ProductResponseDto response = new ProductResponseDto(
                1L,
                "Apple",
                "APL001",
                new BigDecimal("120"),
                20,
                true,
                "/images/apple.jpg",
                1L
        );

        when(productService.getProductById(1L))
                .thenReturn(response);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(1));
    }

    @Test
    void updateProductSuccess() throws Exception {

        ProductRequestDto request = new ProductRequestDto(
                "Apple",
                "APL001",
                new BigDecimal("120"),
                20,
                true,
                "/images/apple.jpg",
                1L
        );

        ProductResponseDto response = new ProductResponseDto(
                1L,
                "Apple",
                "APL001",
                new BigDecimal("120"),
                20,
                true,
                "/images/apple.jpg",
                1L
        );

        when(productService.updateProduct(anyLong(), any(ProductRequestDto.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Apple"));
    }

    @Test
    void deleteProductSuccess() throws Exception {

        doNothing().when(productService).deleteProduct(1L);

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isOk());
    }
}
