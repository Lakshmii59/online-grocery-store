package com.grocery.productcatalogservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grocery.productcatalogservice.dto.CategoryRequestDto;
import com.grocery.productcatalogservice.dto.CategoryResponseDto;
import com.grocery.productcatalogservice.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createCategory_ShouldReturnCreated() throws Exception {

        CategoryRequestDto request =
                new CategoryRequestDto("Fruits", "Fresh fruits");

        CategoryResponseDto response =
                new CategoryResponseDto(1L, "Fruits", "Fresh fruits");

        when(productService.createCategory(any(CategoryRequestDto.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.categoryId").value(1))
                .andExpect(jsonPath("$.categoryName").value("Fruits"));
    }

    @Test
    void getCategoryById_ShouldReturnCategory() throws Exception {

        CategoryResponseDto response =
                new CategoryResponseDto(1L, "Fruits", "Fresh fruits");

        when(productService.getCategoryById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryId").value(1))
                .andExpect(jsonPath("$.categoryName").value("Fruits"));
    }

    @Test
    void getAllCategories_ShouldReturnList() throws Exception {

        List<CategoryResponseDto> list = List.of(
                new CategoryResponseDto(1L, "Fruits", "Fresh fruits"),
                new CategoryResponseDto(2L, "Vegetables", "Fresh vegetables")
        );

        when(productService.getAllCategories()).thenReturn(list);

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void updateCategory_ShouldReturnUpdatedCategory() throws Exception {

        CategoryRequestDto request =
                new CategoryRequestDto("Vegetables", "Updated");

        CategoryResponseDto response =
                new CategoryResponseDto(1L, "Vegetables", "Updated");

        when(productService.updateCategory(eq(1L), any(CategoryRequestDto.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryName").value("Vegetables"));
    }

    @Test
    void deleteCategory_ShouldReturnSuccess() throws Exception {

        doNothing().when(productService).deleteCategory(1L);

        mockMvc.perform(delete("/api/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Category deleted successfully"));
    }
}
