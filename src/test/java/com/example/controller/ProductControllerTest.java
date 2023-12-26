package com.example.controller;

import com.candidatetestspecification.controller.ProductController;
import com.candidatetestspecification.dtos.product.ProductRequestDto;
import com.candidatetestspecification.dtos.product.ProductResponseDto;
import com.candidatetestspecification.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void addProductTest() throws Exception {
        ProductRequestDto productRequestDto = new ProductRequestDto();
        productRequestDto.setEntryDate(new Date());
        productRequestDto.setItemCode("1111");
        productRequestDto.setItemName("Test Inventory 1");
        productRequestDto.setItemQuantity(20L);
        productRequestDto.setStatus("Paid");

        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setEntryDate(new Date());
        productResponseDto.setItemCode("1111");
        productResponseDto.setItemName("Test Inventory 1");
        productResponseDto.setItemQuantity(20L);
        productResponseDto.setStatus("Paid");

        Mockito.when(productService.save(Mockito.any(ProductRequestDto.class))).thenReturn(productResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.itemCode").value("1111"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itemName").value("Test Inventory 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itemQuantity").value(20L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Paid"));
    }

    @Test
    void getAllProductsTest() throws Exception {
        List<Map<String, Object>> records = new ArrayList<>();
        Map<String, Object> product1 = new HashMap<>();
        product1.put("entryDate", "03-01-2023");
        product1.put("itemCode", "11111");
        product1.put("itemName", "Test Inventory 1");
        product1.put("itemQuantity", "20");
        product1.put("status", "Paid");

        Map<String, Object> product2 = new HashMap<>();
        product2.put("entryDate", "03-01-2023");
        product2.put("itemCode", "11111");
        product2.put("itemName", "Test Inventory 2");
        product2.put("itemQuantity", "20");
        product2.put("status", "Paid");

        records.add(product1);
        records.add(product2);

        Map<String, Object> productsMap = new HashMap<>();
        productsMap.put("table", "products");
        productsMap.put("records", records);

        Mockito.when(productService.getAll()).thenReturn(productsMap);

        mockMvc.perform(MockMvcRequestBuilders.get("/products/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.table").value("products"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.records[0].itemName").value("Test Inventory 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.records[1].itemName").value("Test Inventory 2"));
    }
}