package trackDeliveryApp.trackDeliveryApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import trackDeliveryApp.trackDeliveryApp.model.Product;


public interface ProductRepository extends MongoRepository<Product, String> {
}
