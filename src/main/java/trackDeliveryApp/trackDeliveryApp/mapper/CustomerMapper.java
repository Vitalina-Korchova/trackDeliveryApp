package trackDeliveryApp.trackDeliveryApp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import trackDeliveryApp.trackDeliveryApp.dto.CustomerDTO;
import trackDeliveryApp.trackDeliveryApp.model.Customer;


@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDTO toDTO(Customer customer);
    @Mapping(target = "customerId", ignore = true)
    Customer toEntity(CustomerDTO customerDTO);
}
