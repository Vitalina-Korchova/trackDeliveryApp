package trackDeliveryApp.trackDeliveryApp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import java.util.List;

@Document(collection = "Order")
@TypeAlias("Order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    private String orderId;
    @DocumentReference (collection = "Customer")
    private Customer customer;
    @DocumentReference (collection = "Product")
    private List<Product> products;
    private String orderNumber;
    private double totalAmount;
    private String shippingAddress;
    private String status;
    private String createdAt;
}
