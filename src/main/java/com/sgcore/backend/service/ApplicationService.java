package com.sgcore.backend.service;

import com.sgcore.backend.model.ApplicationDoc;
import com.sgcore.backend.repository.ApplicationRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    private final CareerFileService careerFileService;

    public ApplicationService(

            ApplicationRepository applicationRepository,

            CareerFileService careerFileService

    ) {

        this.applicationRepository =
                applicationRepository;

        this.careerFileService =
                careerFileService;
    }

    // =========================
    // GET ALL
    // =========================

    public List<ApplicationDoc> getAllApplications() {

        return applicationRepository.findAll();
    }

    // =========================
    // SAVE APPLICATION
    // =========================

    public ApplicationDoc saveApplication(

            ApplicationDoc appDoc,

            List<MultipartFile> files

    ) throws Exception {

        List<String> uploadedUrls =
                new ArrayList<>();

        // =========================
        // SAFE FILE CHECK
        // =========================

        if (files != null) {

            for (MultipartFile file : files) {

                if (
                        file == null ||

                        file.isEmpty()
                ) {

                    continue;
                }

                String url =
                        careerFileService
                                .uploadResume(file);

                uploadedUrls.add(url);
            }
        }

        // =========================
        // STORE FILE URLS
        // =========================

        appDoc.setFileUrls(uploadedUrls);

        // =========================
        // APPLICATION CODE
        // =========================

        String appCode =
                "APP-" +
                System.currentTimeMillis();

        appDoc.setApplicationCode(
                appCode
        );

        appDoc.setCreatedAt(
                Instant.now()
        );

        return applicationRepository
                .save(appDoc);
    }

    // =========================
    // DELETE
    // =========================

    public void deleteApplication(String id) {

        applicationRepository.deleteById(id);
    }
}