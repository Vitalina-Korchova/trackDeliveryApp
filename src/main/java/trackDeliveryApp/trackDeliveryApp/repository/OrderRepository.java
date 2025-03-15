package trackDeliveryApp.trackDeliveryApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import trackDeliveryApp.trackDeliveryApp.model.Order;


public interface OrderRepository extends MongoRepository<Order,String> {
}
