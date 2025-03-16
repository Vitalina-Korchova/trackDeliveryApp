package trackDeliveryApp.trackDeliveryApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDTO {
    private String orderNumber;
    private String currentLocation;
    private String deliveryStatus;
}
