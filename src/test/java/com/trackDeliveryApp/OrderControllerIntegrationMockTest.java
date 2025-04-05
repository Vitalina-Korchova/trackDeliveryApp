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
import trackDeliveryApp.trackDeliveryApp.dto.OrderDTO;
import trackDeliveryApp.trackDeliveryApp.model.Customer;
import trackDeliveryApp.trackDeliveryApp.model.Order;
import trackDeliveryApp.trackDeliveryApp.model.Product;
import trackDeliveryApp.trackDeliveryApp.repository.CustomerRepository;
import trackDeliveryApp.trackDeliveryApp.repository.OrderRepository;
import trackDeliveryApp.trackDeliveryApp.repository.ProductRepository;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(classes = TrackDeliveryAppApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OrderControllerIntegrationMockTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private String testOrderId;


    @BeforeEach
    void setup() {
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

        Product product2 = new Product();
        product2.setName("Milk");
        product2.setPrice(150.0);

        Product savedProduct1 = productRepository.save(product1);
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
        testOrderId = savedOrder.getOrderId();

    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
        customerRepository.deleteAll();
        productRepository.deleteAll();
    }


    @Test
    void testGetAllOrders() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetOrderById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/{id}", testOrderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("Vitalina"))
                .andExpect(jsonPath("$.totalAmount").value(200.0))
                .andExpect(jsonPath("$.shippingAddress").value("Kobrynska, 22"))
                .andExpect(jsonPath("$.status").value("in process"))
                .andExpect(jsonPath("$.productsName").isArray())
                .andExpect(jsonPath("$.productsName.length()").value(2))
                .andExpect(jsonPath("$.productsName[0]").value("Bread"))
                .andExpect(jsonPath("$.productsName[1]").value("Milk"));


    }

    @Test
    void testCreateOrder() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCustomerName("Vitalina");
        orderDTO.setProductsName(List.of("Bread", "Milk"));
        orderDTO.setTotalAmount(250.0);
        orderDTO.setShippingAddress("Shevchenka, 5");
        orderDTO.setStatus("in process");

        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalAmount").value(250.0))
                .andExpect(jsonPath("$.shippingAddress").value("Shevchenka, 5"))
                .andExpect(jsonPath("$.status").value("in process"))
                .andExpect(jsonPath("$.customerName").value("Vitalina"))
                .andExpect(jsonPath("$.productsName[0]").value("Bread"))
                .andExpect(jsonPath("$.productsName[1]").value("Milk"));
    }


    @Test
    void testUpdateOrder() throws Exception {
        Order existingOrder = orderRepository.findById(testOrderId).orElseThrow();
        Customer existingCustomer = existingOrder.getCustomer();
        Product existingProduct1 = existingOrder.getProducts().get(0);
        Product existingProduct2 = existingOrder.getProducts().get(1);

        // update
        existingOrder.setShippingAddress("Lvivska, 45");
        existingOrder.setStatus("shipped");

        OrderDTO updatedOrderDTO = new OrderDTO();
        updatedOrderDTO.setCustomerName(existingCustomer.getName());
        updatedOrderDTO.setProductsName(List.of(existingProduct1.getName(), existingProduct2.getName()));
        updatedOrderDTO.setTotalAmount(250.0);
        updatedOrderDTO.setShippingAddress("Lvivska, 45");
        updatedOrderDTO.setStatus("shipped");

        mockMvc.perform(MockMvcRequestBuilders.put("/orders/{id}", testOrderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedOrderDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalAmount").value(250.0))
                .andExpect(jsonPath("$.shippingAddress").value("Lvivska, 45"))
                .andExpect(jsonPath("$.status").value("shipped"))
                .andExpect(jsonPath("$.customerName").value("Vitalina"))
                .andExpect(jsonPath("$.productsName[0]").value("Bread"))
                .andExpect(jsonPath("$.productsName[1]").value("Milk"));
    }


    @Test
    void testDeleteOrder() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/orders/{orderId}", testOrderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());


    }
}
