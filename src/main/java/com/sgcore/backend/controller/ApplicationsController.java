package com.sgcore.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgcore.backend.model.ApplicationDoc;
import com.sgcore.backend.service.ApplicationService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationsController {

    private final ApplicationService applicationService;

    private final ObjectMapper objectMapper;

    public ApplicationsController(

            ApplicationService applicationService,

            ObjectMapper objectMapper

    ) {

        this.applicationService = applicationService;

        this.objectMapper = objectMapper;
    }

    // =========================
    // GET ALL APPLICATIONS
    // =========================

    @GetMapping
    public ResponseEntity<List<ApplicationDoc>>
    getAllApplications() {

        try {

            List<ApplicationDoc> apps =
                    applicationService.getAllApplications();

            return ResponseEntity.ok(apps);

        } catch (Exception e) {

            return ResponseEntity
                    .status(500)
                    .body(null);
        }
    }

    // =========================
    // APPLY JOB
    // =========================

    @PostMapping(
            consumes = "multipart/form-data"
    )
    public ResponseEntity<?> apply(

            @RequestPart("application")
            String applicationJson,

            @RequestPart(
                    value = "files",
                    required = false
            )
            List<MultipartFile> files

    ) {

        try {

            ApplicationDoc appDoc =
                    objectMapper.readValue(
                            applicationJson,
                            ApplicationDoc.class
                    );

            ApplicationDoc saved =
                    applicationService.saveApplication(
                            appDoc,
                            files
                    );

            return ResponseEntity.ok(saved);

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .status(500)
                    .body(e.getMessage());
        }
    }

    // =========================
    // DELETE APPLICATION
    // =========================

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteApplication(

            @PathVariable String id

    ) {

        try {

            applicationService.deleteApplication(id);

            return ResponseEntity.ok().build();

        } catch (Exception e) {

            return ResponseEntity
                    .status(500)
                    .body(e.getMessage());
        }
    }
}