package com.grocery.inventoryservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grocery.inventoryservice.dto.InventoryRequestDto;
import com.grocery.inventoryservice.dto.InventoryResponseDto;
import com.grocery.inventoryservice.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InventoryController.class)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InventoryService inventoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createInventorySuccess() throws Exception {

        InventoryRequestDto request = new InventoryRequestDto(
                1L,
                100
        );

        InventoryResponseDto response = new InventoryResponseDto(
                1L,
                1L,
                100
        );

        when(inventoryService.createInventory(any(InventoryRequestDto.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productId").value(1L));
    }


    @Test
    void getAllInventorySuccess() throws Exception {

        InventoryResponseDto response = new InventoryResponseDto(
                1L,
                1L,
                100
        );

        when(inventoryService.getAllInventory())
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/inventory"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productId").value(1L));
    }

    @Test
    void getInventoryByProductIdSuccess() throws Exception {

        InventoryResponseDto response = new InventoryResponseDto(
                1L,
                1L,
                100
        );

        when(inventoryService.getInventoryByProductId(101L))
                .thenReturn(response);

        mockMvc.perform(get("/api/inventory/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(1L));
    }

    @Test
    void updateInventorySuccess() throws Exception {

        InventoryRequestDto request = new InventoryRequestDto(
                1L,
                100
        );

        InventoryResponseDto response = new InventoryResponseDto(
                1L,
                1L,
                100
        );

        when(inventoryService.updateInventory(eq(101L), any(InventoryRequestDto.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/inventory/101")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.availableQuantity").value(100));
    }

    @Test
    void deleteInventorySuccess() throws Exception {

        mockMvc.perform(delete("/api/inventory/101"))
                .andExpect(status().isNoContent());
    }

    @Test
    void checkStockSuccess() throws Exception {

        when(inventoryService.checkStock(101L, 5))
                .thenReturn(true);

        mockMvc.perform(get("/api/inventory/check/101/5"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void decreaseStockSuccess() throws Exception {

        InventoryResponseDto response = new InventoryResponseDto(
                1L,
                101L,
                95
        );

        when(inventoryService.decreaseStock(101L, 5))
                .thenReturn(response);

        mockMvc.perform(put("/api/inventory/101/decrease")
                        .param("quantity", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(101L))
                .andExpect(jsonPath("$.availableQuantity").value(95));
    }

}
