package db1.project.product.service;

import db1.project.product.dto.ProductDTO;
import db1.project.product.model.Product;
import db1.project.product.repository.ProductRepository;
import db1.project.product.translator.ProductTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private ProductTranslator translator;

    @InjectMocks
    private ProductService service;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_create_a_product(){
        ProductDTO productDTO = productDTOBuilder();

        Product product = mock(Product.class);

        when(translator.productDTOToProduct(productDTO)).thenReturn(product);

        when(repository.save(any(Product.class))).thenReturn(product);

        service.createProduct(productDTO);

        verify(repository).save(product);
        verify(translator).productDTOToProduct(productDTO);
        verify(translator).productToProductDTO(product);
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
    public void should_list_all(){
        service.findAll();

        verify(repository).findAll();
    }

    @Test
    public void should_update_product(){
        ProductDTO productDTO = productDTOBuilder();
        Product product = mock(Product.class);

        when(repository.findById(1L)).thenReturn(Optional.ofNullable(product));

        service.update(1L, productDTO);

        verify(product).setPrice(3000L);
        verify(product).setDescription("128gb");
        verify(product).setInventory(50);
        verify(product).setName("Celular");

        verify(repository).findById(1L);
    }

    @Test
    public void should_increment_inventory(){
        ProductDTO productDTO = productDTOBuilder();

        when(translator.productDTOToProduct(productDTO)).thenCallRealMethod();

        Product DTOTranslated = translator.productDTOToProduct(productDTO);

        when(repository.findById(1L)).thenReturn(Optional.of(DTOTranslated));

        int amount = 100;

        Product productToBeIncremented = new Product();

        productToBeIncremented.setInventory(DTOTranslated.getInventory() + amount);

        when(repository.save(DTOTranslated)).thenReturn(productToBeIncremented);

        when(service.increment(productDTO.getId(), amount)).thenCallRealMethod();

        ProductDTO toBeCompared = service.increment(productDTO.getId(), amount);

        assertEquals(150, toBeCompared.getInventory());
    }

    @Test
    public void should_delete(){
        service.delete(1L);

        verify(repository).deleteById(1L);
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


}