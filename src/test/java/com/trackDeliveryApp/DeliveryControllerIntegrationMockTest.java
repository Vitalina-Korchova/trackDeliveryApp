package com.trackDeliveryApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import trackDeliveryApp.trackDeliveryApp.TrackDeliveryAppApplication;
import trackDeliveryApp.trackDeliveryApp.dto.DeliveryDTO;
import trackDeliveryApp.trackDeliveryApp.model.Customer;
import trackDeliveryApp.trackDeliveryApp.model.Delivery;
import trackDeliveryApp.trackDeliveryApp.model.Order;
import trackDeliveryApp.trackDeliveryApp.model.Product;
import trackDeliveryApp.trackDeliveryApp.repository.CustomerRepository;
import trackDeliveryApp.trackDeliveryApp.repository.DeliveryRepository;
import trackDeliveryApp.trackDeliveryApp.repository.OrderRepository;
import trackDeliveryApp.trackDeliveryApp.repository.ProductRepository;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = TrackDeliveryAppApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DeliveryControllerIntegrationMockTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private String testDeliveryId;
    private String testOrderNumber;

    @BeforeEach
    void setup() {
        deliveryRepository.deleteAll();
        orderRepository.deleteAll();
        customerRepository.deleteAll();
        productRepository.deleteAll();

        Customer customer = new Customer();
        customer.setName("Vitalina");
        customer.setEmail("vita@example.com");
        customer.setPhone("0967892233");
        Customer savedCustomer = customerRepository.save(customer);

        Product product1 = new Product();
        product1.setName("Bread");
        product1.setPrice(100.0);
        Product savedProduct1 = productRepository.save(product1);

        Product product2 = new Product();
        product2.setName("Milk");
        product2.setPrice(150.0);
        Product savedProduct2 = productRepository.save(product2);

        Order order = new Order();
        order.setCustomer(savedCustomer);
        order.setProducts(List.of(savedProduct1, savedProduct2));
        order.setOrderNumber("12345");
        order.setTotalAmount(200.0);
        order.setShippingAddress("Kobrynska, 22");
        order.setStatus("in process");
        order.setCreatedAt("05.04.2025");
        Order savedOrder = orderRepository.save(order);
        testOrderNumber = savedOrder.getOrderNumber();

        Delivery delivery = new Delivery();
        delivery.setOrder(savedOrder);
        delivery.setCurrentLocation("Kiev");
        delivery.setDeliveryStatus("In Transit");
        Delivery savedDelivery = deliveryRepository.save(delivery);
        testDeliveryId = savedDelivery.getDeliveryId();
    }

    @AfterEach
    void tearDown() {
        deliveryRepository.deleteAll();
        orderRepository.deleteAll();
        customerRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    void testGetAllDeliveries() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/deliveries")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetDeliveryById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/deliveries/{deliveryId}", testDeliveryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentLocation").value("Kiev"))
                .andExpect(jsonPath("$.deliveryStatus").value("In Transit"))
                .andExpect(jsonPath("$.orderNumber").value(testOrderNumber));
    }

    @Test
    void testCreateDelivery() throws Exception {
        DeliveryDTO deliveryDTO = new DeliveryDTO();
        deliveryDTO.setOrderNumber(testOrderNumber);
        deliveryDTO.setCurrentLocation("Lviv");
        deliveryDTO.setDeliveryStatus("Delivered");

        mockMvc.perform(MockMvcRequestBuilders.post("/deliveries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deliveryDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentLocation").value("Lviv"))
                .andExpect(jsonPath("$.deliveryStatus").value("Delivered"))
                .andExpect(jsonPath("$.orderNumber").value(testOrderNumber));
    }

    @Test
    void testUpdateDelivery() throws Exception {
        DeliveryDTO updatedDeliveryDTO = new DeliveryDTO();
        updatedDeliveryDTO.setOrderNumber(testOrderNumber);
        updatedDeliveryDTO.setCurrentLocation("Odessa");
        updatedDeliveryDTO.setDeliveryStatus("Delivered");

        mockMvc.perform(MockMvcRequestBuilders.put("/deliveries/{deliveryId}", testDeliveryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDeliveryDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentLocation").value("Odessa"))
                .andExpect(jsonPath("$.deliveryStatus").value("Delivered"))
                .andExpect(jsonPath("$.orderNumber").value(testOrderNumber));
    }

    @Test
    void testDeleteDelivery() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/deliveries/{deliveryId}", testDeliveryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
