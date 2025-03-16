package trackDeliveryApp.trackDeliveryApp.service;

import org.springframework.stereotype.Service;
import trackDeliveryApp.trackDeliveryApp.dto.OrderDTO;
import trackDeliveryApp.trackDeliveryApp.model.Customer;
import trackDeliveryApp.trackDeliveryApp.model.Order;
import trackDeliveryApp.trackDeliveryApp.model.Product;
import trackDeliveryApp.trackDeliveryApp.repository.CustomerRepository;
import trackDeliveryApp.trackDeliveryApp.repository.OrderRepository;
import trackDeliveryApp.trackDeliveryApp.repository.ProductRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private  CustomerRepository customerRepository;
    private ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Order createOrder(Order order) {
        if (order.getCreatedAt() == null || order.getCreatedAt().isEmpty()) {
            order.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        }
        if (order.getOrderNumber() == null || order.getOrderNumber().isEmpty()) {
            order.setOrderNumber(generateOrderNumber());
        }
        return orderRepository.save(order);
    }


    public Order updateOrder(String orderId, Order order) {
        Optional<Order> existingOrder = orderRepository.findById(orderId);
        if (existingOrder.isPresent()) {
            Order updatedOrder = existingOrder.get();

            updatedOrder.setProducts(order.getProducts());
            updatedOrder.setTotalAmount(order.getTotalAmount());
            updatedOrder.setShippingAddress(order.getShippingAddress());
            updatedOrder.setStatus(order.getStatus());

            return orderRepository.save(updatedOrder);
        } else {
            throw new RuntimeException("Order with ID " + orderId + " not found");
        }
    }

    public void deleteOrder(String orderId) {
        orderRepository.deleteById(orderId);
    }

    public Customer findCustomerByName(String name) {
        return customerRepository.findCustomerByName(name)
                .orElseThrow(() -> new RuntimeException("Customer not found with name: " + name));
    }

    public Product findProductByName(String name) {
        return productRepository.findProductByName(name)
                .orElseThrow(() -> new RuntimeException("Product not found with name: " + name));
    }

    public String generateOrderNumber() {
        String orderNumber = "000";
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
        orderNumber += now.format(formatter);
        return orderNumber;
    }
}
