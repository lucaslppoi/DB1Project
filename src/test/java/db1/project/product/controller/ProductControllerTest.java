package db1.project.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import db1.project.product.dto.ProductDTO;
import db1.project.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebMvcTest
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Test
    void createProduct() throws Exception {
        ProductDTO productDTO = productDTOBuilder();

        when(service.createProduct(productDTO)).thenReturn(productDTO);

        String json = new ObjectMapper().writeValueAsString(productDTO);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/product/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);


        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("name").value("Celular"));
    }

    @Test
    void findById() throws Exception {
        ProductDTO productDTO = productDTOBuilder();

        when(service.findById(1L)).thenReturn(productDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/product/search/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("Celular"))
                .andExpect(jsonPath("id").value(1L));
    }

    @Test
    void notfindById() throws Exception {
        when(service.findById(1L)).thenReturn(null);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/product/search/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    void findAll() throws Exception {
        ProductDTO productDTO = productDTOBuilder();
        ProductDTO productDTO1 = productDTOBuilder();
        productDTO1.setName("Computador");

        when(service.findAll()).thenReturn(Arrays.asList(productDTO, productDTO1));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/product/listall")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Celular"))
                .andExpect(jsonPath("$[1].name").value("Computador"));

    }

    @Test
    void notFindAll() throws Exception {
        List<ProductDTO> emptyList = Collections.<ProductDTO>emptyList();

        when(service.findAll()).thenReturn(emptyList);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/product/listall")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);


        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    void update() throws Exception {
        ProductDTO productDTO = productDTOBuilder();
        ProductDTO productDTO1 = productDTOBuilder();
        productDTO1.setName("Computador");

        Long id = 1L;

        when(service.update(id, productDTO)).thenReturn(productDTO1);

        String json = new ObjectMapper().writeValueAsString(productDTO);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/product/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("name").value("Computador"));
    }

    @Test
    void increment() throws Exception {
        ProductDTO productDTO = productDTOBuilder();
        productDTO.setPrice(productDTO.getPrice() + 100);

        when(service.increment(1L, 100)).thenReturn(productDTO);

        String json = new ObjectMapper().writeValueAsString(productDTO);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/product/inventory/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);


        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("Celular"))
                .andExpect(jsonPath("id").value(1L))
                .andExpect((jsonPath("price").value(3100L)));
    }

    @Test
    void delete() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/product/delete/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    private ProductDTO productDTOBuilder(){
        ProductDTO prod = new ProductDTO(1L, "Celular", 3000L, 50, "Celular");

        return prod;
    }
}