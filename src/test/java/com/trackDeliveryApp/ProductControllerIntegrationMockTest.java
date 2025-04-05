package com.trackDeliveryApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import trackDeliveryApp.trackDeliveryApp.TrackDeliveryAppApplication;
import trackDeliveryApp.trackDeliveryApp.dto.ProductDTO;
import trackDeliveryApp.trackDeliveryApp.model.Product;
import trackDeliveryApp.trackDeliveryApp.repository.ProductRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = TrackDeliveryAppApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private String testProductId;

    @BeforeEach
    void setup() {
        productRepository.deleteAll();
        Product product = new Product();
        product.setName("Test Product Milk");
        product.setCategory("Food");
        product.setPrice(100.0);
        product.setWeight(10.0);
        product.setStatus("Available");
        product.setCreatedAt("2024-04-05T10:00:00");

        Product savedProduct = productRepository.save(product);
        testProductId = savedProduct.getProductId();
    }

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    void testGetAllProducts() throws Exception {
        mockMvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetProductById() throws Exception {
        mockMvc.perform(get("/products/{id}", testProductId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Product Milk"))
                .andExpect(jsonPath("$.category").value("Food"))
                .andExpect(jsonPath("$.price").value(100.0))
                .andExpect(jsonPath("$.weight").value(10.0))
                .andExpect(jsonPath("$.status").value("Available"));
    }

    @Test
    void testCreateProduct() throws Exception {
        ProductDTO productDTO = new ProductDTO("New Product", 50.0, "Books", 5.0, "In Stock");

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Product"))
                .andExpect(jsonPath("$.category").value("Books"))
                .andExpect(jsonPath("$.price").value(50.0))
                .andExpect(jsonPath("$.weight").value(5.0))
                .andExpect(jsonPath("$.status").value("In Stock"));
    }

    @Test
    void testUpdateProduct() throws Exception {
        ProductDTO updatedDTO = new ProductDTO("Updated Product", 150.0, "Tech", 7.0, "Out of Stock");

        mockMvc.perform(put("/products/{id}", testProductId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Product"))
                .andExpect(jsonPath("$.price").value(150.0))
                .andExpect(jsonPath("$.category").value("Tech"))
                .andExpect(jsonPath("$.weight").value(7.0))
                .andExpect(jsonPath("$.status").value("Out of Stock"));

        mockMvc.perform(get("/products/{id}", testProductId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Product"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/products/{id}", testProductId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
