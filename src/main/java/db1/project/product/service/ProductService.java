package db1.project.product.service;

import db1.project.product.dto.ProductDTO;
import db1.project.product.model.Product;
import db1.project.product.repository.ProductRepository;
import db1.project.product.translator.ProductTranslator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductTranslator productTranslator;

    public ProductService(ProductRepository productRepository, ProductTranslator productTranslator) {
        this.productRepository = productRepository;
        this.productTranslator = productTranslator;
    }

    @Transactional
    public ProductDTO createProduct (ProductDTO productDTO){
        Product product = productTranslator.productDTOToProduct(productDTO);
        Product savedProduct = productRepository.save(product);
        return productTranslator.productToProductDTO(savedProduct);
    }

    @Transactional(readOnly = true)
    public ProductDTO findById (Long id){
        return productRepository.findById(id)
                .map(product -> productTranslator.productToProductDTO(product))
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream().map(product -> productTranslator.productToProductDTO(product))
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductDTO update (Long id, ProductDTO productDTO){
        return productRepository.findById(id).map(product -> {
            product.setPrice(productDTO.getPrice());
            product.setDescription(productDTO.getDescription());
            product.setInventory(productDTO.getInventory());
            product.setName(productDTO.getName());
            Product savedProduct = productRepository.save(product);
            return productTranslator.productToProductDTO(savedProduct);
        }).orElse(null);
    }

    @Transactional
    public ProductDTO increment (Long id, int amount){
        return productRepository.findById(id).map(product -> {
            product.setInventory(product.getInventory()+amount);
            Product save = productRepository.save(product);
            return productTranslator.productToProductDTO(save);
        }).orElse(null);
    }

    @Transactional
    public void delete(Long id){
        productRepository.deleteById(id);
    }
}