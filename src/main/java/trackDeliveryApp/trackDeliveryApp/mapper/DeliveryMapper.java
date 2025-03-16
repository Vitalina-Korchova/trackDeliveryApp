package trackDeliveryApp.trackDeliveryApp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import trackDeliveryApp.trackDeliveryApp.dto.DeliveryDTO;
import trackDeliveryApp.trackDeliveryApp.model.Delivery;


@Mapper(componentModel = "spring")
public interface DeliveryMapper {

    @Mapping(source = "deliveryId", target = "orderNumber")
    @Mapping(source = "currentLocation", target = "currentLocation")
    @Mapping(source = "deliveryStatus", target = "deliveryStatus")
    DeliveryDTO toDTO(Delivery delivery);

    @Mapping(source = "orderNumber", target = "deliveryId")
    @Mapping(source = "currentLocation", target = "currentLocation")
    @Mapping(source = "deliveryStatus", target = "deliveryStatus")
    Delivery toDelivery(DeliveryDTO deliveryDTO);
}
