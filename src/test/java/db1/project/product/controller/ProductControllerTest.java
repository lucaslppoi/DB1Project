package db1.project.product.controller;

import db1.project.product.dto.ProductDTO;
import db1.project.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@ExtendWith(SpringExtension.class)
@SpringBootApplication
@AutoConfigureMockMvc
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ProductService service;

    @Test
    void createProduct() throws Exception {
        ProductDTO productDTO = productDTOBuilder();
        entityManager.persist(productDTO);
        entityManager.flush();

        when(service.createProduct(productDTO)).thenReturn(productDTO);

        mockMvc.perform(post("/product/create"))
//                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) jsonPath("$[0].name", is("Celular")));
    }

    @Test
    void findById() throws Exception {
        ProductDTO productDTO = productDTOBuilder();
        entityManager.persist(productDTO);
        entityManager.flush();

        when(service.findById(1L)).thenReturn(productDTO);

        mockMvc.perform(get("/search/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) jsonPath("$[0].name", is("Celular")));
    }

    @Test
    void notfindById() throws Exception {
        when(service.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/search/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findAll() throws Exception {
        List<ProductDTO> list = mock(List.class);

        when(service.findAll()).thenReturn(list);

        ProductDTO productDTO = productDTOBuilder();
        ProductDTO productDTO1 = productDTOBuilder();
        productDTO1.setName("Computador");

        list.add(0, productDTO);
        list.add(1, productDTO1);

        entityManager.persist(list);
        entityManager.flush();

        mockMvc.perform(get("/product/listall"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) jsonPath("$[0].name", is("Celular")))
                .andExpect((ResultMatcher) jsonPath("$[1].name", is("Computador")));
    }

    @Test
    void notFindAll() throws Exception {
        List<ProductDTO> emptyList = Collections.<ProductDTO>emptyList();

        when(service.findAll()).thenReturn(emptyList);

        mockMvc.perform(get("/listall"))
                .andExpect(status().isNotFound());
    }

    @Test
    void update() throws Exception {
        ProductDTO productDTO = productDTOBuilder();
        ProductDTO productDTO1 = productDTOBuilder();
        productDTO1.setName("Computador");

        entityManager.persist(productDTO);
        entityManager.persist(productDTO1);
        entityManager.flush();

        when(service.update(1L, productDTO)).thenReturn(productDTO1);

        mockMvc.perform(get("/update/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) jsonPath("$[0].name", is("Computador")));
    }

    @Test
    void increment() throws Exception {
        ProductDTO productDTO = productDTOBuilder();
        productDTO.setPrice(productDTO.getPrice() + 100);

        entityManager.persist(productDTO);
        entityManager.flush();

        when(service.increment(1L, 100)).thenReturn(productDTO);

        mockMvc.perform(get("/inventory/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) jsonPath("$[0].price", is(3100L)));
    }

    @Test
    void delete() throws Exception {
        ProductDTO productDTO = productDTOBuilder();
        entityManager.persist(productDTO);
        entityManager.flush();

        mockMvc.perform(get("/delete/1"))
                .andExpect(status().isOk());
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