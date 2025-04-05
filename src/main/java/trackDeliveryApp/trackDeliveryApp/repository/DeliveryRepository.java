package trackDeliveryApp.trackDeliveryApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import trackDeliveryApp.trackDeliveryApp.model.Delivery;


@Repository
public interface DeliveryRepository extends MongoRepository<Delivery, String> {
}
