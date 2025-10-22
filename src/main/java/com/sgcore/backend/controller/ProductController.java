package com.sgcore.backend.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sgcore.backend.model.Product;
import com.sgcore.backend.service.ProductService;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://127.0.0.1:5173",
        "https://machine-mechanics-frontend.vercel.app",
        "https://machine-mechanics-frontend-hz6uq0apg.vercel.app"
})
public class ProductController {

    @Autowired
    private ProductService productService;

    private static final String UPLOAD_DIR = "uploads/";

    // === SINGLE IMAGE UPLOAD ===
    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadImage(@RequestParam("file") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // ✅ Dynamically determine base URL
            String baseUrl = System.getenv("BASE_URL");
            if (baseUrl == null || baseUrl.isEmpty()) {
                baseUrl = "http://localhost:8099"; // fallback for local development
            }
            String imageUrl = baseUrl + "/uploads/" + fileName;

            response.put("message", "File uploaded successfully");
            response.put("fileUrl", imageUrl);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    // === MULTIPLE IMAGE UPLOAD ===
    @PostMapping("/upload/multiple")
    public ResponseEntity<Map<String, Object>> uploadMultipleImages(@RequestParam("files") MultipartFile[] files) {
        Map<String, Object> response = new HashMap<>();
        List<String> urls = new ArrayList<>();

        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));

            // ✅ Same dynamic base URL logic here
            String baseUrl = System.getenv("BASE_URL");
            if (baseUrl == null || baseUrl.isEmpty()) {
                baseUrl = "http://localhost:8099";
            }

            for (MultipartFile file : files) {
                if (file.isEmpty()) continue;

                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR + fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                urls.add(baseUrl + "/uploads/" + fileName);
            }

            response.put("uploadedFiles", urls);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    // === CRUD ===
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product updatedProduct) {
        Product existingProduct = productService.getProductById(id);
        if (existingProduct == null) {
            return ResponseEntity.notFound().build();
        }

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setImageUrl(updatedProduct.getImageUrl());

        Product saved = productService.addProduct(existingProduct);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
    }
}
