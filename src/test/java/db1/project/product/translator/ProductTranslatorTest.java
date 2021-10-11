package db1.project.product.translator;

import db1.project.product.dto.ProductDTO;
import db1.project.product.model.Product;
import db1.project.product.repository.ProductRepository;
import db1.project.product.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductTranslatorTest {

    @Mock
    private ProductService productService;

    @Spy
    @InjectMocks
    private ProductTranslator productTranslator;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void fromEntityToDTO(){
//        Product myProduct = new Product();
//
//        productRepository.save(myProduct);
//
//        ProductDTO productDTO = new ProductDTO();
//
//        Assertions.assertSame(productTranslator.productToProductDTO(myProduct), productDTO);
//
//
//    }


}