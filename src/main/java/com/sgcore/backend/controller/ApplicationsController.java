package com.sgcore.backend.controller;

import com.sgcore.backend.model.ApplicationDoc;
import com.sgcore.backend.repository.ApplicationRepository;
import com.sgcore.backend.service.ApplicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationsController {

    private final ApplicationService applicationService;
    private final ObjectMapper objectMapper;
    private final ApplicationRepository applicationRepository;

    public ApplicationsController(
            ApplicationService applicationService,
            ObjectMapper objectMapper,
            ApplicationRepository applicationRepository
    ) {
        this.applicationService = applicationService;
        this.objectMapper = objectMapper;
        this.applicationRepository = applicationRepository;
    }

    // --------------------------------------------
    // VIEW ALL APPLICATIONS
    // --------------------------------------------
    @GetMapping
    public List<ApplicationDoc> list() {
        return applicationRepository.findAll();
    }

    // --------------------------------------------
    // GET ONE APPLICATION
    // --------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable String id) {
        return applicationRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // --------------------------------------------
    // FILE DOWNLOAD ENDPOINT (FINAL & CORRECT)
    // --------------------------------------------
    @GetMapping("/file/{fileId}")
    public ResponseEntity<?> downloadFile(@PathVariable String fileId) {
        try {
            var resource = applicationService.getFileResource(fileId);
            if (resource == null) return ResponseEntity.notFound().build();

            String contentType = resource.getContentType();
            if (contentType == null) contentType = "application/pdf";

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\"" + resource.getFilename() + "\"")
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(new InputStreamResource(resource.getInputStream()));

        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("Error downloading: " + e.getMessage());
        }
    }

    // --------------------------------------------
    // SUBMIT APPLICATION  (YOUR ORIGINAL CODE)
    // --------------------------------------------
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> submit(MultipartHttpServletRequest request) {
        try {
            String appJson = request.getParameter("application");

            if (appJson == null) {
                MultipartFile jsonFile = request.getFile("application");

                if (jsonFile == null || jsonFile.isEmpty()) {
                    return ResponseEntity.badRequest().body("Missing 'application' part");
                }
                appJson = new String(jsonFile.getBytes(), StandardCharsets.UTF_8);
            }

            ApplicationDoc appDoc = objectMapper.readValue(appJson, ApplicationDoc.class);

            List<MultipartFile> files = new ArrayList<>();

            Iterator<String> names = request.getFileNames();
            while (names.hasNext()) {
                String partName = names.next();

                if ("application".equals(partName)) continue;

                List<MultipartFile> foundFiles = request.getFiles(partName);
                if (foundFiles != null) {
                    files.addAll(foundFiles);
                }
            }

            ApplicationDoc saved = applicationService.saveApplication(appDoc, files);

            return ResponseEntity.ok(saved);

        } catch (IOException ex) {
            return ResponseEntity.status(500).body("Error processing JSON: " + ex.getMessage());

        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error saving: " + ex.getMessage());
        }
    }
}
