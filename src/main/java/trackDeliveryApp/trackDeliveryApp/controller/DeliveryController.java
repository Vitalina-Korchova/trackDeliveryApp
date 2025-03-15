package trackDeliveryApp.trackDeliveryApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import trackDeliveryApp.trackDeliveryApp.model.Delivery;
import trackDeliveryApp.trackDeliveryApp.service.DeliveryService;

import java.util.List;

@RestController
@RequestMapping("/deliveries")
public class DeliveryController {
    private final DeliveryService deliveryService;

    @Autowired
    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping
    public List<Delivery> getAllDeliveries() {
        return deliveryService.getAllDeliveries();
    }

    @GetMapping("/{deliveryId}")
    public Delivery getDeliveryById(@PathVariable String deliveryId) {
        return deliveryService.getDeliveryById(deliveryId);
    }

    @PostMapping
    public Delivery createDelivery(@RequestBody Delivery delivery) {
        return deliveryService.createDelivery(delivery);
    }

    @PutMapping("/{deliveryId}")
    public Delivery updateDelivery(@PathVariable String deliveryId, @RequestBody Delivery delivery) {
        return deliveryService.updateDelivery(deliveryId, delivery);
    }

    @DeleteMapping("/{deliveryId}")
    public void deleteDelivery(@PathVariable String deliveryId) {
        deliveryService.deleteDelivery(deliveryId);
    }
}
