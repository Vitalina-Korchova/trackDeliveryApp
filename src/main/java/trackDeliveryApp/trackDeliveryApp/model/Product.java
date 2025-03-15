package trackDeliveryApp.trackDeliveryApp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document (collection = "Product")
@TypeAlias("Product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private String productId;
    private String name;
    private String category;
    private double weight;
    private double price;
    private String status;
    private String createdAt;
}
