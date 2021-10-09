package db1.project.product.mapper;

import db1.project.product.dto.ProductDTO;
import db1.project.product.model.Product;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {

    ProductDTO prodToProdDTO (Product product);

    Product prodDTOToProd (ProductDTO productDTO);
}
