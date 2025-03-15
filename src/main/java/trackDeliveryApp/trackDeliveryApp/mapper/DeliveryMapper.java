package trackDeliveryApp.trackDeliveryApp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import trackDeliveryApp.trackDeliveryApp.dto.DeliveryDTO;
import trackDeliveryApp.trackDeliveryApp.model.Delivery;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {
    @Mapping(source = "order.customer.name", target = "customerName")
    @Mapping(source = "order.totalAmount", target = "totalAmount")
    @Mapping(source = "order.shippingAddress", target = "shippingAddress")
    DeliveryDTO toDTO(Delivery delivery);

    @Mapping(target = "deliveryId", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "courierName", ignore = true)
    @Mapping(target = "deliveryCompany", ignore = true)
    @Mapping(target = "estimatedDeliveryDate", ignore = true)
    Delivery toEntity(DeliveryDTO deliveryDTO);
}
