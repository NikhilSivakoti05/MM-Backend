package com.sgcore.backend.controller;

import com.sgcore.backend.model.CoreCapability;
import com.sgcore.backend.repository.CoreCapabilityRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/core-capabilities")
public class CoreCapabilityController {

    private final CoreCapabilityRepository repo;

    public CoreCapabilityController(CoreCapabilityRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<CoreCapability> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public CoreCapability create(@RequestBody CoreCapability c) {
        return repo.save(c);
    }

    @GetMapping("/service/{serviceId}")
    public List<CoreCapability> getByService(@PathVariable String serviceId) {
        return repo.findByServiceId(serviceId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        repo.deleteById(id);
    }
}
