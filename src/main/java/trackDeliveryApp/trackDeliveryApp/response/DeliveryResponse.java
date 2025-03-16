package trackDeliveryApp.trackDeliveryApp.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliveryResponse {
    private String orderNumber;
    private String customerName;
    private double totalAmount;
    private String shippingAddress;
    private String currentLocation;
    private String deliveryStatus;
}
