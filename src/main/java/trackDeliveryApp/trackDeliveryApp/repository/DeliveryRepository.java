package trackDeliveryApp.trackDeliveryApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import trackDeliveryApp.trackDeliveryApp.model.Delivery;

public interface DeliveryRepository extends MongoRepository<Delivery, String> {
}
