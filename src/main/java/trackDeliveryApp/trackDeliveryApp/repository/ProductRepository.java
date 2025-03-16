package trackDeliveryApp.trackDeliveryApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import trackDeliveryApp.trackDeliveryApp.model.Customer;
import trackDeliveryApp.trackDeliveryApp.model.Product;

import java.util.List;
import java.util.Optional;


public interface ProductRepository extends MongoRepository<Product, String> {
    Optional<Product> findProductByName(String name);
}
