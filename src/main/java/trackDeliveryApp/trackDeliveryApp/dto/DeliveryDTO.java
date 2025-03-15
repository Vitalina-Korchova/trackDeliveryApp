package trackDeliveryApp.trackDeliveryApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDTO {
    private String customerName;
    private double totalAmount;
    private String shippingAddress;
    private String currentLocation;
    private String deliveryStatus;
    private String trackingNumber;
}
