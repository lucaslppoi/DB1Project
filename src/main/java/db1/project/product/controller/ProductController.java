package db1.project.product.controller;

import db1.project.product.dto.ProductDTO;
import db1.project.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ProductDTO createProduct (@RequestBody ProductDTO productDTO) {
        System.out.println("123");
        return productService.createProduct(productDTO);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<?> findById (@PathVariable Long id) {
        ProductDTO byId = productService.findById(id);

        if (Objects.isNull(byId)) {
            return new ResponseEntity<>("Produto não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(byId);
    }

    @GetMapping("/listall")
    public ResponseEntity<List> findAll () {
        List<ProductDTO> prod = productService.findAll();

        if (prod.isEmpty()) {
            return new ResponseEntity("Não há produtos cadastrados", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(prod);
    }

    @PutMapping("/update/{id}")
    public ProductDTO update (@PathVariable Long id, @RequestBody ProductDTO productDTO){
         return productService.update(id, productDTO);
    }

    @PutMapping("/inventory/{id}")
    public ProductDTO increment (@PathVariable Long id , @RequestParam("amount") int amount){
        return productService.increment(id, amount);
    }

    @DeleteMapping("/delete/{id}")
    public void delete (@PathVariable Long id){
        productService.delete(id);
    }
}
