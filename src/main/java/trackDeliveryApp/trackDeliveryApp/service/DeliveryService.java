package trackDeliveryApp.trackDeliveryApp.service;

import org.springframework.stereotype.Service;
import trackDeliveryApp.trackDeliveryApp.model.Delivery;
import trackDeliveryApp.trackDeliveryApp.model.Order;
import trackDeliveryApp.trackDeliveryApp.model.Product;
import trackDeliveryApp.trackDeliveryApp.repository.DeliveryRepository;
import trackDeliveryApp.trackDeliveryApp.repository.OrderRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private OrderRepository orderRepository;

    public DeliveryService(DeliveryRepository deliveryRepository,OrderRepository orderRepository) {
        this.deliveryRepository = deliveryRepository;
        this.orderRepository = orderRepository;

    }

    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    public Delivery getDeliveryById(String deliveryId) {
        return deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new RuntimeException("Delivery not found"));
    }

    public Delivery createDelivery(Delivery delivery) {
        delivery.setDeliveryCompany("Nova Poshta");
        delivery.setEstimatedDeliveryDate(LocalDate.now().plusDays(2).toString());
        delivery.setTrackingNumber(generateTrackingNumber());
        return deliveryRepository.save(delivery);
    }


    public Delivery updateDelivery(String deliveryId, Delivery delivery) {
        Optional<Delivery> existingDelivery = deliveryRepository.findById(deliveryId);
        if (existingDelivery.isPresent()) {
            Delivery updatedDelivery = existingDelivery.get();

            updatedDelivery.setCurrentLocation(delivery.getCurrentLocation());
            updatedDelivery.setDeliveryStatus(delivery.getDeliveryStatus());

            return deliveryRepository.save(updatedDelivery);
        } else {
            throw new RuntimeException("Delivery with ID " + deliveryId + " not found");
        }
    }

    public void deleteDelivery(String deliveryId) {
        deliveryRepository.deleteById(deliveryId);
    }

    private String generateTrackingNumber() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
        return "NP" + uuid;
    }

    public Order  findOrderByOrderNumber(String number) {
        return orderRepository.findOrderByOrderNumber(number)
                .orElseThrow(() -> new RuntimeException("Order not found with number: " + number));
    }
}
