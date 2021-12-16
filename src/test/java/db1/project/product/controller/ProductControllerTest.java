package db1.project.product.controller;

import db1.project.product.dto.ProductDTO;
import db1.project.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ProductDTO productDTO;

    @Mock
    private ProductService service;

    @Mock
    private ResponseEntity entity;

    @InjectMocks
    private ProductController controller;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void createProduct() {
        controller.createProduct(productDTO);

        verify(service).createProduct(productDTO);
    }

    @Test
    void findById() {
        when(service.findById(1L)).thenReturn(productDTO);

        ResponseEntity<?> result = controller.findById(1L);

//        assertEquals(productDTO, result);
    }

    @Test
    void notfindById() {
        when(service.findById(1L)).thenReturn(null);

        ResponseEntity<?> result = controller.findById(1L);

        assertEquals("Produto não encontrado", result.getBody());
    }

    @Test
    void findAll() {
        List<ProductDTO> list = mock(List.class);
        ProductDTO productDTO2 = mock(ProductDTO.class);

        when(productDTO.getId()).thenReturn(1L);
        when(productDTO2.getId()).thenReturn(2L);

        list.add(0, productDTO);
        list.add(1, productDTO2);

        when(service.findAll()).thenReturn(list);

        ResponseEntity<List> test = controller.findAll();

        assertNotNull(test);
    }

    @Test
    void update() {
        controller.update(1L, productDTO);

        verify(service).update(1L, productDTO);
    }

    @Test
    void increment() {
        controller.increment(1L, 100);

        verify(service).increment(1L, 100);
    }

    @Test
    void delete() {
        controller.delete(1L);

        verify(service).delete(1L);
    }
}