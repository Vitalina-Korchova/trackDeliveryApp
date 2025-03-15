package trackDeliveryApp.trackDeliveryApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trackDeliveryApp.trackDeliveryApp.model.Product;
import trackDeliveryApp.trackDeliveryApp.repository.ProductRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(String productId) {
        return productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product createProduct(Product product) {
        if (product.getCreatedAt() == null || product.getCreatedAt().isEmpty()) {
            product.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        }
        return productRepository.save(product);
    }

    public Product updateProduct(String productId, Product product) {
        Optional<Product> existingProduct = productRepository.findById(productId);
        if (existingProduct.isPresent()) {
            Product updatedProduct = existingProduct.get();

            updatedProduct.setName(product.getName());
            updatedProduct.setCategory(product.getCategory());
            updatedProduct.setPrice(product.getPrice());
            updatedProduct.setWeight(product.getWeight());
            updatedProduct.setStatus(product.getStatus());

            return productRepository.save(updatedProduct);
        } else {
            throw new RuntimeException("Product with ID " + productId + " not found");
        }
    }
    public void deleteProduct(String productId) {
        productRepository.deleteById(productId);
    }
}
