package trackDeliveryApp.trackDeliveryApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import trackDeliveryApp.trackDeliveryApp.model.Customer;

import java.util.Optional;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    Optional<Customer> findCustomerByName(String name);
}
