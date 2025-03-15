package trackDeliveryApp.trackDeliveryApp.service;

import org.springframework.stereotype.Service;
import trackDeliveryApp.trackDeliveryApp.model.Customer;
import trackDeliveryApp.trackDeliveryApp.model.Order;
import trackDeliveryApp.trackDeliveryApp.model.Product;
import trackDeliveryApp.trackDeliveryApp.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Order createOrder(Order order) {
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
}
