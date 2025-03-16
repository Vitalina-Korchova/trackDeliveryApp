package trackDeliveryApp.trackDeliveryApp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import trackDeliveryApp.trackDeliveryApp.dto.OrderDTO;
import trackDeliveryApp.trackDeliveryApp.model.Customer;
import trackDeliveryApp.trackDeliveryApp.model.Order;
import trackDeliveryApp.trackDeliveryApp.model.Product;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "customer.name", target = "customerName")
    @Mapping(source = "products", target = "productsName", qualifiedByName = "mapProductNames")
    OrderDTO toDTO(Order order);

    @Named("mapProductNames")
    static List<String> mapProductNames(List<Product> products) {
        return products != null ? products.stream().map(Product::getName).collect(Collectors.toList()) : null;
    }

    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Order toEntity(OrderDTO orderDTO, Customer customer, List<Product> products);
}
