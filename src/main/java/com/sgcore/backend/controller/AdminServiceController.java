
package com.sgcore.backend.controller;

import com.sgcore.backend.model.Service;
import com.sgcore.backend.repository.ServiceRepository;
import com.sgcore.backend.service.ProductImageService;
import com.sgcore.backend.service.ProductService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin/services")

public class AdminServiceController {

    private final ServiceRepository repo;

    private final ProductImageService productImageService;

    public AdminServiceController(
            ServiceRepository repo,
            ProductImageService productImageService
    ) {

        this.repo = repo;
        this.productImageService = productImageService;
    }

    // =========================
    // CREATE SERVICE
    // =========================

    @PostMapping(consumes = "multipart/form-data")
    public Service create(

            @RequestParam String title,

            @RequestParam String description,

            @RequestParam(required = false)
            String category,

            @RequestParam(required = false)
            MultipartFile[] images,

            @RequestParam(required = false)
            String faqs

    ) throws Exception {

        Service service = new Service();

        service.setTitle(title);
        service.setDescription(description);
        service.setCategory(category);

        List<String> urls = new ArrayList<>();

        if (images != null) {

            for (MultipartFile file : images) {

                urls.add(
                        productImageService.uploadImage(file)
                );
            }
        }

        service.setImageUrls(urls);

        if (faqs != null && !faqs.isEmpty()) {

            service.setFaqs(
                    ProductService.convertFaqJsonToList(faqs)
            );
        }

        return repo.save(service);
    }

    // =========================
    // UPDATE SERVICE
    // =========================

    @PutMapping(
            value = "/{id}",
            consumes = "multipart/form-data"
    )
    public Service update(

            @PathVariable String id,

            @RequestParam String title,

            @RequestParam String description,

            @RequestParam(required = false)
            String category,

            @RequestParam(required = false)
            MultipartFile[] images,

            @RequestParam(required = false)
            String faqs

    ) throws Exception {

        Service existing =
                repo.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Service not found"
                                )
                        );

        existing.setTitle(title);
        existing.setDescription(description);
        existing.setCategory(category);

        if (faqs != null && !faqs.isEmpty()) {

            existing.setFaqs(
                    ProductService.convertFaqJsonToList(faqs)
            );
        }

        if (images != null && images.length > 0) {

            List<String> urls = new ArrayList<>();

            for (MultipartFile file : images) {

                urls.add(
                        productImageService.uploadImage(file)
                );
            }

            existing.setImageUrls(urls);
        }

        return repo.save(existing);
    }

    // =========================
    // DELETE SERVICE
    // =========================

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable String id
    ) {

        Service service =
                repo.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Service not found"
                                )
                        );

        repo.delete(service);

        return ResponseEntity.ok().body(
                "Service deleted successfully"
        );
    }

    // =========================
    // GET ALL SERVICES
    // =========================

    @GetMapping
    public List<Service> getAll() {

        return repo.findAll();
    }

    // =========================
    // GET SINGLE SERVICE
    // =========================

    @GetMapping("/{id}")
    public Service getOne(
            @PathVariable String id
    ) {

        return repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Service not found"
                        )
                );
    }
}
