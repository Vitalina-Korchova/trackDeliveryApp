package trackDeliveryApp.trackDeliveryApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import trackDeliveryApp.trackDeliveryApp.dto.OrderDTO;
import trackDeliveryApp.trackDeliveryApp.mapper.OrderMapper;
import trackDeliveryApp.trackDeliveryApp.model.Order;
import trackDeliveryApp.trackDeliveryApp.service.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @GetMapping
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders()
                .stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{orderId}")
    public OrderDTO getOrderById(@PathVariable String orderId) {
        Order order = orderService.getOrderById(orderId);
        return orderMapper.toDTO(order);
    }

    @PostMapping
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
        Order order = orderMapper.toEntity(orderDTO);
        Order savedOrder = orderService.createOrder(order);
        return orderMapper.toDTO(savedOrder);
    }

    @PutMapping("/{orderId}")
    public OrderDTO updateOrder(@PathVariable String orderId, @RequestBody OrderDTO orderDTO) {
        Order order = orderMapper.toEntity(orderDTO);
        Order updatedOrder = orderService.updateOrder(orderId, order);
        return orderMapper.toDTO(updatedOrder);
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable String orderId) {
        orderService.deleteOrder(orderId);
    }
}
