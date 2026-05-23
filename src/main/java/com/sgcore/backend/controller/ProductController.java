package com.sgcore.backend.controller;

import com.sgcore.backend.model.Product;

import com.sgcore.backend.service.ProductImageService;
import com.sgcore.backend.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductImageService productImageService;


    // =========================
    // CREATE PRODUCT
    // =========================

    @PostMapping(consumes = "multipart/form-data")
    public Product addProduct(

            @RequestParam String name,

            @RequestParam String description,

            @RequestParam(required = false)
            Double price,

            @RequestParam(required = false)
            MultipartFile[] images,

            @RequestParam(required = false)
            String faqs

    ) throws Exception {

        Product product = new Product();

        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);

        // =========================
        // IMAGE UPLOAD
        // =========================

        List<String> imageUrls = new ArrayList<>();

        if (images != null) {

            for (MultipartFile img : images) {

                String url =
                        productImageService.uploadImage(img);

                imageUrls.add(url);
            }
        }

        product.setImageUrls(imageUrls);

        // =========================
        // FAQS
        // =========================

        if (faqs != null && !faqs.isEmpty()) {

            product.setFaqs(
                    ProductService.convertFaqJsonToList(faqs)
            );
        }

        return productService.save(product);
    }


    // =========================
    // GET ALL PRODUCTS
    // =========================

    @GetMapping
    public List<Product> getAll() {

        return productService.getAll();
    }


    // =========================
    // GET PRODUCT BY ID
    // =========================

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(
            @PathVariable String id
    ) {

        Product product =
                productService.getById(id);

        if (product == null) {

            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(product);
    }


    // =========================
    // UPDATE PRODUCT
    // =========================

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<Product> update(

            @PathVariable String id,

            @RequestParam String name,

            @RequestParam String description,

            @RequestParam(required = false)
            Double price,

            @RequestParam(required = false)
            MultipartFile[] images,

            @RequestParam(required = false)
            String faqs

    ) throws Exception {

        Product existing =
                productService.getById(id);

        if (existing == null) {

            return ResponseEntity.notFound().build();
        }

        existing.setName(name);
        existing.setDescription(description);
        existing.setPrice(price);

        // =========================
        // NEW IMAGE UPLOADS
        // =========================

        if (images != null) {

            List<String> urls =
                    existing.getImageUrls();

            if (urls == null) {

                urls = new ArrayList<>();
            }

            for (MultipartFile img : images) {

                String url =
                        productImageService.uploadImage(img);

                urls.add(url);
            }

            existing.setImageUrls(urls);
        }

        // =========================
        // FAQ UPDATE
        // =========================

        if (faqs != null && !faqs.isEmpty()) {

            existing.setFaqs(
                    ProductService.convertFaqJsonToList(faqs)
            );
        }

        Product updated =
                productService.save(existing);

        return ResponseEntity.ok(updated);
    }


    // =========================
    // DELETE PRODUCT
    // =========================

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable String id
    ) {

        Product existing =
                productService.getById(id);

        if (existing == null) {

            return ResponseEntity.notFound().build();
        }

        productService.delete(id);

        return ResponseEntity.ok().build();
    }
}