package com.sgcore.backend.service;

import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.models.FileCreateRequest;
import io.imagekit.sdk.models.results.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

@Service
public class CareerFileService {

    @Autowired
    @Qualifier("careerImageKit")
    private ImageKit imageKit;


    public String uploadResume(MultipartFile file) throws Exception {

        validateFile(file);

        FileCreateRequest request =
                new FileCreateRequest(
                        file.getBytes(),
                        file.getOriginalFilename()
                );

        request.setFolder("/careers/resumes");

        Result result = imageKit.upload(request);

        return result.getUrl();
    }


    private void validateFile(MultipartFile file) {

        String type = file.getContentType();

        boolean valid =
                "application/pdf".equals(type)
                        || "application/msword".equals(type)
                        || "application/vnd.openxmlformats-officedocument.wordprocessingml.document".equals(type);

        if (!valid) {

            throw new RuntimeException(
                    "Only PDF/DOC/DOCX files allowed"
            );
        }
    }
}