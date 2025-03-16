package trackDeliveryApp.trackDeliveryApp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import trackDeliveryApp.trackDeliveryApp.dto.DeliveryDTO;
import trackDeliveryApp.trackDeliveryApp.dto.OrderDTO;
import trackDeliveryApp.trackDeliveryApp.mapper.DeliveryMapper;
import trackDeliveryApp.trackDeliveryApp.mapper.OrderMapper;
import trackDeliveryApp.trackDeliveryApp.model.Customer;
import trackDeliveryApp.trackDeliveryApp.model.Delivery;
import trackDeliveryApp.trackDeliveryApp.model.Order;
import trackDeliveryApp.trackDeliveryApp.model.Product;
import trackDeliveryApp.trackDeliveryApp.response.DeliveryResponse;
import trackDeliveryApp.trackDeliveryApp.service.DeliveryService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/deliveries")
public class DeliveryController {
    private final DeliveryService deliveryService;
    private DeliveryMapper deliveryMapper;

    @Autowired
    public DeliveryController(DeliveryService deliveryService,DeliveryMapper deliveryMapper) {
        this.deliveryService = deliveryService;
        this.deliveryMapper = deliveryMapper;
    }

    @GetMapping
    public List<DeliveryResponse> getAllDeliveries() {
        return deliveryService.getAllDeliveries().stream()
                .map(deliveryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{deliveryId}")
    public DeliveryResponse getDeliveryById(@PathVariable String deliveryId) {
        Delivery delivery = deliveryService.getDeliveryById(deliveryId);
        return deliveryMapper.toResponse(delivery);
    }


    @PostMapping
    public DeliveryDTO createDelivery(@Valid @RequestBody DeliveryDTO deliveryDTO) {

        Order order = deliveryService.findOrderByOrderNumber(deliveryDTO.getOrderNumber());

        Delivery delivery = new Delivery();
        delivery.setOrder(order);
        delivery.setCurrentLocation(deliveryDTO.getCurrentLocation());
        delivery.setDeliveryStatus(deliveryDTO.getDeliveryStatus());

        Delivery savedDelivery = deliveryService.createDelivery(delivery);
        return deliveryMapper.toDTO(savedDelivery);
    }

    @PutMapping("/{deliveryId}")
    public DeliveryDTO updateDelivery(@Valid @PathVariable String deliveryId, @RequestBody DeliveryDTO deliveryDTO) {

        Order order = deliveryService.findOrderByOrderNumber(deliveryDTO.getOrderNumber());

        Delivery delivery = new Delivery();
        delivery.setOrder(order);
        delivery.setCurrentLocation(deliveryDTO.getCurrentLocation());
        delivery.setDeliveryStatus(deliveryDTO.getDeliveryStatus());

        Delivery updatedDelivery = deliveryService.updateDelivery(deliveryId, delivery);
        return deliveryMapper.toDTO(updatedDelivery);
    }

    @DeleteMapping("/{deliveryId}")
    public void deleteDelivery(@PathVariable String deliveryId) {
        deliveryService.deleteDelivery(deliveryId);
    }
}
