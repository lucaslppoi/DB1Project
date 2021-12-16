package db1.project.product.translator;

import db1.project.product.dto.ProductDTO;
import db1.project.product.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductTranslator {

    public Product productDTOToProduct(ProductDTO productDTO){
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setDescription(productDTO.getDescription());
        product.setInventory(productDTO.getInventory());
        product.setPrice(productDTO.getPrice());
        product.setName(productDTO.getName());
        return product;
    }

    public ProductDTO productToProductDTO (Product product){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setDescription(product.getDescription());
        productDTO.setInventory(product.getInventory());
        productDTO.setPrice(product.getPrice());
        productDTO.setName(product.getName());
        return productDTO;
    }


}
