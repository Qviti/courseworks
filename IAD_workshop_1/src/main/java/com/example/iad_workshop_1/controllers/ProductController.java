package com.example.iad_workshop_1.controllers;

import com.example.iad_workshop_1.models.Product;
import com.example.iad_workshop_1.services.ProductService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    @GetMapping
    public List<Product> getProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<String> uploadProductImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        productService.saveProductImage(id, file);
        return ResponseEntity.ok("Image uploaded successfully");
    }

    @DeleteMapping("/{id}/image")
    public ResponseEntity<String> deleteProductImage(@PathVariable Long id) throws IOException {
        productService.deleteProductImage(id);
        return ResponseEntity.ok("Image deleted successfully");
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> downloadProductImage(@PathVariable Long id) {
        byte[] image = productService.getProductImage(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        productService.addProduct(product);
        return ResponseEntity.ok("Product added successfully");
    }
}




