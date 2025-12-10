package com.sgcore.backend.controller;

import com.sgcore.backend.model.Portfolio;
import com.sgcore.backend.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired; // New Import
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/portfolio")

public class PortfolioController {

    private final PortfolioService portfolioService;

    // Manual Constructor Injection replacing @RequiredArgsConstructor
    // The @Autowired annotation is optional here since it's the only constructor,
    // but it explicitly shows Spring's intention.
    @Autowired 
    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    // CREATE
    @PostMapping
    public Portfolio create(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("image") MultipartFile image
    ) throws IOException {

        Portfolio p = new Portfolio();
        p.setTitle(title);
        p.setDescription(description);
        p.setCategory(category);

        String base64 = Base64.getEncoder().encodeToString(image.getBytes());
        p.setImageBase64(base64);

        return portfolioService.create(p);
    }

    // READ
    @GetMapping
    public List<Portfolio> getAll() {
        return portfolioService.findAll();
    }

    // UPDATE
    @PutMapping("/{id}")
    public Portfolio update(
            @PathVariable String id,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) throws IOException {

        Portfolio data = new Portfolio();
        data.setTitle(title);
        data.setDescription(description);
        data.setCategory(category);

        if (image != null) {
            String base64 = Base64.getEncoder().encodeToString(image.getBytes());
            data.setImageBase64(base64);
        }

        return portfolioService.update(id, data);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        portfolioService.delete(id);
    }
}