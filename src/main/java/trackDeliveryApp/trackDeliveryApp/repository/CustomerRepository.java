package trackDeliveryApp.trackDeliveryApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import trackDeliveryApp.trackDeliveryApp.model.Customer;

import java.util.Optional;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
    Optional<Customer> findCustomerByName(String name);
    // метод для перевірки дубліката телефона
    boolean existsByPhone(String phone);
}
