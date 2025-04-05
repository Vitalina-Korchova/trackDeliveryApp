package com.trackDeliveryApp;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import trackDeliveryApp.trackDeliveryApp.TrackDeliveryAppApplication;
import trackDeliveryApp.trackDeliveryApp.dto.CustomerDTO;
import trackDeliveryApp.trackDeliveryApp.model.Customer;
import trackDeliveryApp.trackDeliveryApp.repository.CustomerRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = TrackDeliveryAppApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CustomerControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private String testCustomerId;

    @BeforeEach
    void setup() {
        customerRepository.deleteAll();
        Customer customer = new Customer();
        customer.setName("Raiden Ei");
        customer.setEmail("raiden@gmail.com");
        customer.setPhone("0959993390");

        Customer savedCustomer = customerRepository.save(customer);
        testCustomerId = savedCustomer.getCustomerId();
//        System.out.println("id: "+ testCustomerId);
    }

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    void testGetAllCustomers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    void testGetCustomerById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/{id}", testCustomerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Raiden Ei"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("raiden@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("0959993390"));
    }

    @Test
    void testCreateCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("Lisa");
        customerDTO.setEmail("lisa@example.com");
        customerDTO.setPhone("0951234567");

        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Lisa"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("lisa@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("0951234567"));
    }

    @Test
    void testUpdateCustomer() throws Exception {

        Customer customer = new Customer();
        customer.setName("Klee Updated");
        customer.setEmail("klee@example.com");
        customer.setPhone("0960000000");

        Customer savedCustomer = customerRepository.save(customer);
        String updateCustomerId = savedCustomer.getCustomerId();

        savedCustomer.setPhone("0991234455");

        mockMvc.perform(MockMvcRequestBuilders.put("/customers/{id}", updateCustomerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(savedCustomer)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("0991234455"));

        // Перевірка збережених змін
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/{id}", updateCustomerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Klee Updated"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("klee@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("0991234455"));
    }

    @Test
    void testDeleteCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/customers/{id}", testCustomerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/{id}", testCustomerId))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError()); //не маю обробника для 400 того заюзано 500
    }
}
