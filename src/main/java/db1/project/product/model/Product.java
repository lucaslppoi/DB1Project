package db1.project.product.model;

import db1.project.product.dto.ProductDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private float price;

    private Integer inventory;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Product productDTOToProduct(ProductDTO productDTO){
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setDescription(productDTO.getDescription());
        product.setInventory(productDTO.getInventory());
        product.setPrice(productDTO.getPrice());
        product.setName(productDTO.getName());
        return product;
    }
}
