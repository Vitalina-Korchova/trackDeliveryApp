package trackDeliveryApp.trackDeliveryApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private String customerName;
    private List<String> productsName;
    private double totalAmount;
    private String shippingAddress;
    private String status;
}
