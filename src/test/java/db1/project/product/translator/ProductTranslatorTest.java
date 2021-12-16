package db1.project.product.translator;

import db1.project.product.dto.ProductDTO;
import db1.project.product.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class ProductTranslatorTest {

    @InjectMocks
    private ProductTranslator translator;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_translate_from_dto_to_entity(){
        ProductDTO productDTO = productDTOBuilder();

        Product result = translator.productDTOToProduct(productDTO);

        assertEquals(result.getInventory(), productDTO.getInventory());
        assertEquals(result.getInventory(), productDTO.getInventory());
        assertEquals(result.getDescription(), productDTO.getDescription());
        assertEquals(result.getName(), productDTO.getName());
    }

    @Test
    public void should_translate_from_entity_to_dto(){
        Product product = productBuilder();

        ProductDTO result = translator.productToProductDTO(product);

        assertEquals(result.getInventory(), product.getInventory());
        assertEquals(result.getInventory(), product.getInventory());
        assertEquals(result.getDescription(), product.getDescription());
        assertEquals(result.getName(), product.getName());
    }

    private ProductDTO productDTOBuilder(){
        ProductDTO prod = new ProductDTO();

        prod.setPrice(3000L);
        prod.setName("Celular");
        prod.setDescription("128gb");
        prod.setInventory(50);
        prod.setId(1L);

        return prod;
    }

    private Product productBuilder(){
        Product prod = new Product();

        prod.setPrice(3000L);
        prod.setName("Celular");
        prod.setDescription("128gb");
        prod.setInventory(50);
        prod.setId(1L);

        return prod;
    }

}