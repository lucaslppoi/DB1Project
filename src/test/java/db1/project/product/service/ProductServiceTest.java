package db1.project.product.service;

import db1.project.product.dto.ProductDTO;
import db1.project.product.model.Product;
import db1.project.product.repository.ProductRepository;
import db1.project.product.translator.ProductTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.security.RunAs;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Captor
    private ArgumentCaptor<Product> captor;

    @Captor
    private ArgumentCaptor<ProductDTO> captorDTO;

    @InjectMocks
    private ProductService service;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_create_a_product(){
        ProductDTO productDTO = productDTOBuilder();
//        Product product = mock(Product.class);
        Product product = ProductTranslator.productDTOToProduct(productDTO);

        when(repository.save(any(Product.class))).thenReturn(product);

        service.createProduct(productDTO);

        verify(repository).save(ProductTranslator.productDTOToProduct(productDTO));
    }

    @Test
    public void should_find_by_id(){
        ProductDTO productDTO = productDTOBuilder();

        service.findById(productDTO.getId());

        verify(repository).findById(productDTO.getId());
        assertNotNull(repository.findById(productDTO.getId()));
    }

    @Test
    public void should_not_find_by_id(){
        service.findById(productDTOBuilder().getId());

        verify(repository).findById(1L);
        assertNull(service.findById(1L));
    }

    @Test
    public void should_update_product(){
        ProductDTO productDTO = productDTOBuilder();
        Product prod = mock(Product.class);

        when(repository.findById(2L)).thenReturn(Optional.ofNullable(prod));

        service.update(2L, productDTO);

        //capturar o retorno e verificar se é igual ao builder
    }

    @Test
    public void should_increment(){
        ProductDTO productDTO = productDTOBuilder();

        Product abc = ProductTranslator.productDTOToProduct(productDTO);
//
        when(repository.findById(1L)).thenReturn(Optional.of(abc));

        int amount = 100;

        Product dce = new Product();

        dce.setInventory(abc.getInventory() + amount);

        when(repository.save(abc)).thenReturn(dce);

        ProductDTO prod = service.increment(productDTO.getId(), amount);

        assertEquals(150, prod.getInventory());
    }

    private ProductDTO productDTOBuilder(){
        ProductDTO prod = new ProductDTO();

        prod.setPrice(3000L);
        prod.setName("bbbb");
        prod.setDescription("aaa");
        prod.setInventory(50);
        prod.setId(1L);

        return prod;
    }
}