package trackDeliveryApp.trackDeliveryApp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import trackDeliveryApp.trackDeliveryApp.dto.ProductDTO;
import trackDeliveryApp.trackDeliveryApp.model.Product;


@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDTO(Product product);
    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Product toEntity(ProductDTO productDTO);
}
