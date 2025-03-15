package trackDeliveryApp.trackDeliveryApp.service;

import org.springframework.stereotype.Service;
import trackDeliveryApp.trackDeliveryApp.model.Delivery;
import trackDeliveryApp.trackDeliveryApp.repository.DeliveryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;

    public DeliveryService(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    public Delivery getDeliveryById(String deliveryId) {
        return deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new RuntimeException("Delivery not found"));
    }

    public Delivery createDelivery(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }

    public Delivery updateDelivery(String deliveryId, Delivery delivery) {
        Optional<Delivery> existingDelivery = deliveryRepository.findById(deliveryId);
        if (existingDelivery.isPresent()) {
            Delivery updatedDelivery = existingDelivery.get();

            updatedDelivery.setCourierName(delivery.getCourierName());
            updatedDelivery.setDeliveryCompany(delivery.getDeliveryCompany());
            updatedDelivery.setEstimatedDeliveryDate(delivery.getEstimatedDeliveryDate());
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
}
