package com.gamercein.backend.controller;

import com.gamercein.backend.model.Product;
import com.gamercein.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*") // Allow frontend to access this API
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // Get all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Get product by ID
    @GetMapping("/api/products/{id}")
public ResponseEntity<Product> getProductById(@PathVariable String id) {
    Optional<Product> product = productRepository.findById(id);
    return product.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
}



    // Add a new product
    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    // Update product
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable String id, @RequestBody Product updatedProduct) {
        return productRepository.findById(id)
            .map(product -> {
                product.setName(updatedProduct.getName());
                product.setCategory(updatedProduct.getCategory());
                product.setPrice(updatedProduct.getPrice());
                product.setDescription(updatedProduct.getDescription());
                product.setImage(updatedProduct.getImage());
                return productRepository.save(product);
            })
            .orElseGet(() -> {
                updatedProduct.setId(id);
                return productRepository.save(updatedProduct);
            });
    }

    // Delete product
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        productRepository.deleteById(id);
    }
}
