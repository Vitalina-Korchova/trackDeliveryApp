package trackDeliveryApp.trackDeliveryApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import trackDeliveryApp.trackDeliveryApp.model.Order;


import java.util.Optional;


public interface OrderRepository extends MongoRepository<Order,String> {
    Optional<Order> findOrderByOrderNumber(String number);
}
