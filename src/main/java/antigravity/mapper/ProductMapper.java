package antigravity.mapper;

import antigravity.domain.entity.Product;
import antigravity.dto.ProductResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class); // 2

//    @Mapping(target = "createdAt", expression = "java(product.getCreatedAt().format(DateTimeFormatter.ofPattern(\"yyyy-MM-dd HH:mm:ss\")))") // 4
//    @Mapping(target = "updatedAt", expression = "java(product.getCreatedAt().format(DateTimeFormatter.ofPattern(\"yyyy-MM-dd HH:mm:ss\")))") // 4
//    @Mapping(target = "deletedAt", expression = "java(product.getCreatedAt().format(DateTimeFormatter.ofPattern(\"yyyy-MM-dd HH:mm:ss\")))") // 4
    ProductResponseDto orderToDto(Product product);
}
