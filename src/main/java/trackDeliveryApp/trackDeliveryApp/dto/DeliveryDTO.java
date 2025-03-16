package trackDeliveryApp.trackDeliveryApp.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDTO {
    //вже згенерований системою
    private String orderNumber;
    @NotEmpty(message = "Current location must not be empty")
    private String currentLocation;
    @NotEmpty(message = "Delivery Status must not be empty")
    private String deliveryStatus;
}
