package db1.project.product.service;

import db1.project.product.dto.ProductDTO;
import db1.project.product.model.Product;
import db1.project.product.repository.ProductRepository;
import db1.project.product.translator.ProductTranslator;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Spy
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductTranslator productTranslator;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validateCreatingNewProduct(){
        ProductDTO expectedProductDTO = new ProductDTO();

        Product expectedProduct = new Product();

        when(productRepository.save(any(Product.class))).thenReturn(expectedProduct);
        when(productService.createProduct(any(ProductDTO.class))).thenReturn(expectedProductDTO);

        ProductDTO productDTO = productService.createProduct(expectedProductDTO);

        Assertions.assertEquals(expectedProductDTO.getId(), productDTO.getId());
    }

    @Test
    void validateFindById () {
        ProductDTO expectedProductDTO = new ProductDTO();

        Product expectedProduct = new Product();

        when(productRepository.findById(expectedProductDTO.getId())).thenReturn(Optional.of(expectedProduct));

        assertThat(productService.findById(expectedProductDTO.getId())).isEqualTo(expectedProduct.getId());
    }

    @Test
    void validadeUpdate(){
        ProductDTO expectedProductDTO = new ProductDTO();

        Product expectedProduct = new Product();

        when(productRepository.save(any(Product.class))).thenReturn(expectedProduct);
        when(productService.update(expectedProductDTO.getId(), expectedProductDTO)).thenThrow(new RuntimeException("Test Exception"));

        Assertions.assertNotEquals(productTranslator.productToProductDTO(expectedProduct), expectedProductDTO);
    }

    @Test
    void validateIncrement(){
        ProductDTO expectedProductDTO = new ProductDTO();
        expectedProductDTO.setInventory(10);
        int incrementValue = 50;
        expectedProductDTO.setInventory(expectedProductDTO.getInventory() + incrementValue);

        Assertions.assertEquals(60, expectedProductDTO.getInventory());
    }
}