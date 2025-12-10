package com.sgcore.backend.controller;

import com.sgcore.backend.model.FAQ;
import com.sgcore.backend.model.Product;
import com.sgcore.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")

public class ProductController {

    @Autowired
    private ProductService productService;

    // CREATE PRODUCT
    @PostMapping
    public Product addProduct(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam(value = "price", required = false) Double price,
            @RequestParam(value = "images", required = false) MultipartFile[] images,
            @RequestParam(value = "faqs", required = false) String faqsJson
    ) throws Exception {

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);

        // MULTIPLE IMAGES
        List<String> base64Images = new ArrayList<>();
        if (images != null) {
            for (MultipartFile img : images) {
                base64Images.add(Base64.getEncoder().encodeToString(img.getBytes()));
            }
        }
        product.setImagesBase64(base64Images);

        // FAQs
        if (faqsJson != null && !faqsJson.isEmpty()) {
            List<FAQ> faqList = ProductService.convertFaqJsonToList(faqsJson);
            product.setFaqs(faqList);
        }

        return productService.addProduct(product);
    }

    // GET ALL
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // GET PRODUCT BY ID (FIXED)
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        Product p = productService.getProductById(id);
        if (p == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(p);
    }

    // UPDATE PRODUCT
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable String id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam(value = "price", required = false) Double price,
            @RequestParam(value = "images", required = false) MultipartFile[] images,
            @RequestParam(value = "faqs", required = false) String faqsJson
    ) throws Exception {

        Product existing = productService.getProductById(id);
        if (existing == null) return ResponseEntity.notFound().build();

        existing.setName(name);
        existing.setDescription(description);
        existing.setPrice(price);

        // Replace images only if provided
        if (images != null) {
            List<String> base64Images = new ArrayList<>();
            for (MultipartFile img : images) {
                base64Images.add(Base64.getEncoder().encodeToString(img.getBytes()));
            }
            existing.setImagesBase64(base64Images);
        }

        // FAQs
        if (faqsJson != null && !faqsJson.isEmpty()) {
            List<FAQ> faqList = ProductService.convertFaqJsonToList(faqsJson);
            existing.setFaqs(faqList);
        }

        return ResponseEntity.ok(productService.addProduct(existing));
    }

    // DELETE PRODUCT
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
    }
}
