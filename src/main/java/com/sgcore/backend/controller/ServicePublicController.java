package com.sgcore.backend.controller;

import com.sgcore.backend.model.CoreCapability;
import com.sgcore.backend.model.Service;
import com.sgcore.backend.repository.CoreCapabilityRepository;
import com.sgcore.backend.repository.ServiceRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/services")

public class ServicePublicController {

    private final ServiceRepository serviceRepo;
    private final CoreCapabilityRepository coreRepo;

    public ServicePublicController(
            ServiceRepository serviceRepo,
            CoreCapabilityRepository coreRepo
    ) {
        this.serviceRepo = serviceRepo;
        this.coreRepo = coreRepo;
    }

    // ✅ GET ALL SERVICES
    @GetMapping
    public List<Service> getAllServices() {
        return serviceRepo.findAll();
    }

    // ✅ GET SINGLE SERVICE BY ID
    @GetMapping("/{id}")
    public Map<String, Object> getService(@PathVariable String id) {

        Service service = serviceRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        List<CoreCapability> coreCapabilities =
                coreRepo.findByServiceId(id);

        Map<String, Object> response = new HashMap<>();

        response.put("service", service);
        response.put("coreCapabilities", coreCapabilities);

        return response;
    }
}