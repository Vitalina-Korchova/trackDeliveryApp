package trackDeliveryApp.trackDeliveryApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import trackDeliveryApp.trackDeliveryApp.model.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {
}
