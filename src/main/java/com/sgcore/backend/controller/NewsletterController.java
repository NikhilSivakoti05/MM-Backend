package com.sgcore.backend.controller;

import com.sgcore.backend.model.NewsletterSubscriber;
import com.sgcore.backend.repository.NewsletterRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/newsletter")

public class NewsletterController {

    private final NewsletterRepository newsletterRepository;

    public NewsletterController(NewsletterRepository newsletterRepository) {
        this.newsletterRepository = newsletterRepository;
    }

    @GetMapping
    public List<NewsletterSubscriber> getAllSubscribers() {
        return newsletterRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> addSubscriber(@RequestBody NewsletterSubscriber subscriber) {
        if (newsletterRepository.existsByEmail(subscriber.getEmail())) {
            return ResponseEntity.badRequest().body("Email already subscribed");
        }
        newsletterRepository.save(subscriber);
        return ResponseEntity.ok(subscriber);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscriber(@PathVariable String id) {
        newsletterRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
