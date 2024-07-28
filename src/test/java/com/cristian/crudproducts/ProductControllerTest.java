package com.cristian.crudproducts;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.cristian.crudproducts.controller.ProductController;
import com.cristian.crudproducts.dto.ProductRequest;
import com.cristian.crudproducts.entity.Category;
import com.cristian.crudproducts.entity.Product;
import com.cristian.crudproducts.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@WebMvcTest(ProductController.class)
@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testFindById_ExistingId_ShouldReturnProduct() throws Exception {
        // Given
        Long productId = 1L;
        Category category = new Category(1L, "Test Category", "Test Description");
        Product mockProduct = new Product(productId, "Test Product", "Test Description", 10.0, category);
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

    @Test
    public void save_product() throws Exception {
        // Mock data
        Category mockCategory = new Category(1L, "Electronics", "Electronic Items");
        Product mockProduct = new Product(1L, "Test Product", "Test Description", 10.0, mockCategory);

        ProductRequest productRequest = new ProductRequest("Test Product", "Test Description", 10.0, 1L);

        // Wrap the Product in a ResponseEntity
        ResponseEntity<Product> response = ResponseEntity.status(HttpStatus.CREATED).body(mockProduct);

        // Correct stubbing to return a Product object
        when(productService.save(any(ProductRequest.class))).thenReturn(response.getBody());

        // Test the ProductController
        ResponseEntity<Product> test = productController.save(productRequest);

        assertThat(response.getBody().getId()).isEqualTo(test.getBody().getId());
        assertThat(response.getBody().getName()).isEqualTo(test.getBody().getName());
        assertThat(response.getBody().getDescription()).isEqualTo(test.getBody().getDescription());
        assertThat(response.getBody().getPrice()).isEqualTo(test.getBody().getPrice());
        assertThat(response.getBody().getCategory()).isEqualTo(test.getBody().getCategory());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void show_all_page_products() throws Exception {
        Category category = new Category();
        category.setId(1L);
        category.setName("Electronics");
        category.setDescription("Electronic Items");

        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Test Product");
        product1.setDescription("Test Description");
        product1.setPrice(10.0);
        product1.setCategory(category);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Test Product 2");
        product2.setDescription("Test Description 2");
        product2.setPrice(20.0);
        product2.setCategory(category);

        Product product3 = new Product();
        product3.setId(3L);
        product3.setName("Test Product 3");
        product3.setDescription("Test Description 3");
        product3.setPrice(30.0);
        product3.setCategory(category);

        // quiero una lista de productos para usar en el test, usando Page y Pageable
        // Crear una lista de productos
        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);

        // Crear un objeto Page a partir de la lista de productos
        // Asumiendo que quieres la primera página con un tamaño igual al número de productos
        Page<Product> productPage = new PageImpl<>(productList, PageRequest.of(0, productList.size()), productList.size());


        // Mock the service layer to return an empty Page<Product> instead of null
        when(productService.findAll(PageRequest.of(0, 3))).thenReturn(productPage);
    
        // Test the ProductController
        ResponseEntity<Page<Product>> test = productController.findAll(PageRequest.of(0, 3));

        assertThat(test.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(test.getBody().getContent().size()).isEqualTo(3);
        assertThat(test.getBody().getContent().get(0)).isEqualTo(product1);
        assertThat(test.getBody().getContent().get(1)).isEqualTo(product2);
        assertThat(test.getBody().getContent().get(2)).isEqualTo(product3);

    }

    @Test
    public void delete_product_by_id() throws Exception {
        // Mock data
        Category mockCategory = new Category(1L, "Electronics", "Electronic Items");
        Product mockProduct = new Product(1L, "Test Product", "Test Description", 10.0, mockCategory);

        // Wrap the Product in a ResponseEntity
        ResponseEntity<Product> response = ResponseEntity.ok(mockProduct);

        // Correct stubbing to return a Product object
        when(productService.deleteById(1L)).thenReturn(response.getBody());

        // Test the ProductController
        ResponseEntity<Product> test = productController.deleteById(1L);

        assertThat(response.getBody().getId()).isEqualTo(test.getBody().getId());
        assertThat(response.getBody().getName()).isEqualTo(test.getBody().getName());
        assertThat(response.getBody().getDescription()).isEqualTo(test.getBody().getDescription());
        assertThat(response.getBody().getPrice()).isEqualTo(test.getBody().getPrice());
        assertThat(response.getBody().getCategory()).isEqualTo(test.getBody().getCategory());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void update_product_by_id() throws Exception {
        // Mock data
        Category mockCategory = new Category(1L, "Electronics", "Electronic Items");
        Product mockProduct = new Product(1L, "Test Product", "Test Description", 10.0, mockCategory);

        ProductRequest productRequest = new ProductRequest("Test Product", "Test Description", 10.0, 1L);

        // Wrap the Product in a ResponseEntity
        ResponseEntity<Product> response = ResponseEntity.ok(mockProduct);

        // Correct stubbing to return a Product object
        when(productService.updateById(1L, productRequest)).thenReturn(response.getBody());

        // Test the ProductController
        ResponseEntity<Product> test = productController.updateById(1L, productRequest);

        assertThat(response.getBody().getId()).isEqualTo(test.getBody().getId());
        assertThat(response.getBody().getName()).isEqualTo(test.getBody().getName());
        assertThat(response.getBody().getDescription()).isEqualTo(test.getBody().getDescription());
        assertThat(response.getBody().getPrice()).isEqualTo(test.getBody().getPrice());
        assertThat(response.getBody().getCategory()).isEqualTo(test.getBody().getCategory());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}