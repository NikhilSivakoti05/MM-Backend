package com.sgcore.backend.controller;



import com.sgcore.backend.model.Testimonial;
import com.sgcore.backend.repository.TestimonialRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/testimonials")

public class TestimonialController {

    private final TestimonialRepository testimonialRepository;

    public TestimonialController(TestimonialRepository testimonialRepository) {
        this.testimonialRepository = testimonialRepository;
    }

    @GetMapping
    public List<Testimonial> getAll() {
        return testimonialRepository.findAll();
    }

    @PostMapping
    public Testimonial create(@RequestBody Testimonial testimonial) {
        return testimonialRepository.save(testimonial);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Testimonial> update(@PathVariable String id, @RequestBody Testimonial updated) {
        Optional<Testimonial> existing = testimonialRepository.findById(id);
        if (existing.isEmpty()) return ResponseEntity.notFound().build();

        Testimonial t = existing.get();
        t.setContent(updated.getContent());
        t.setAuthor(updated.getAuthor());
        t.setRole(updated.getRole());

        return ResponseEntity.ok(testimonialRepository.save(t));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!testimonialRepository.existsById(id)) return ResponseEntity.notFound().build();
        testimonialRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

