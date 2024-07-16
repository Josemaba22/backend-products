package com.cristian.crudproducts;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.cristian.crudproducts.controller.ProductController;
import com.cristian.crudproducts.entity.Category;
import com.cristian.crudproducts.entity.Product;
import com.cristian.crudproducts.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testFindById_ExistingId_ShouldReturnProduct() throws Exception {
        // Given
        Long productId = 1L;
        Product mockProduct = new Product(productId, "Test Product", "Test Description", 10.0, new Category(1L, "Test Category", "Test Description"));
        when(productService.findById(productId)).thenReturn(Optional.of(mockProduct));

        // When & Then
        mockMvc.perform(get("/api/v1/product/{id}", productId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(mockProduct)));
    }

    @Test
    public void testFindById_NonExistingId_ShouldReturnNotFound() throws Exception {
        // Given
        Long invalidProductId = 2L;
        when(productService.findById(invalidProductId)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/products/{id}", invalidProductId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}