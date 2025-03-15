package trackDeliveryApp.trackDeliveryApp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document (collection = "Delivery")
@TypeAlias("Delivery")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {
    @Id
    private String deliveryId;
    @DocumentReference (collection = "Order")
    private Order order;
    private String courierName;
    private String deliveryCompany;
    private String estimatedDeliveryDate;
    private String currentLocation;
    private String deliveryStatus;
    private String trackingNumber;
}
