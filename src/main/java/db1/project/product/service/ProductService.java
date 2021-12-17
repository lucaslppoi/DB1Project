package db1.project.product.service;

import db1.project.product.dto.ProductDTO;
import db1.project.product.model.Product;
import db1.project.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductDTO createProduct (ProductDTO productDTO){
        Product product = Product.productDTOToProduct(productDTO);
        Product savedProduct = productRepository.save(product);
        return ProductDTO.productToProductDTO(savedProduct);
    }

    @Transactional(readOnly = true)
    public ProductDTO findById (Long id){
        return productRepository.findById(id)
                .map(ProductDTO::productToProductDTO)
                .orElse(null);
    }


    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream().map(ProductDTO::productToProductDTO)
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
            return ProductDTO.productToProductDTO(savedProduct);
        }).orElse(null);
    }

    @Transactional
    public ProductDTO increment (Long id, int amount){
        return productRepository.findById(id).map(product -> {
            product.setInventory(product.getInventory()+amount);
            Product save = productRepository.save(product);
            return ProductDTO.productToProductDTO(save);
        }).orElse(null);
    }

    @Transactional
    public void delete(Long id){
        productRepository.deleteById(id);
    }
}