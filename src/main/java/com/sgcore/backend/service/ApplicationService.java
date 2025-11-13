package com.sgcore.backend.service;



import com.sgcore.backend.model.ApplicationDoc;
import com.sgcore.backend.repository.ApplicationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.mongodb.gridfs.ReactiveGridFsResource; // not used; keep imports minimal
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final GridFsTemplate gridFsTemplate;
    private final ObjectMapper objectMapper;

    public ApplicationService(ApplicationRepository applicationRepository,
                              GridFsTemplate gridFsTemplate,
                              ObjectMapper objectMapper) {
        this.applicationRepository = applicationRepository;
        this.gridFsTemplate = gridFsTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Save application doc and store files to GridFS.
     * @param appDoc already-populated ApplicationDoc (without fileIds)
     * @param files list of MultipartFile to store (may be empty)
     * @return saved ApplicationDoc
     */
    public ApplicationDoc saveApplication(ApplicationDoc appDoc, List<MultipartFile> files) throws IOException {
        List<String> savedFileIds = new ArrayList<>();

        for (MultipartFile mf : files) {
            if (mf == null || mf.isEmpty()) continue;
            String original = mf.getOriginalFilename();
            String ext = FilenameUtils.getExtension(original);
            String contentType = mf.getContentType() == null ? "application/octet-stream" : mf.getContentType();

            try (InputStream in = mf.getInputStream()) {
                ObjectId id = gridFsTemplate.store(in, original, contentType);
                savedFileIds.add(id.toHexString());
            }
        }

        appDoc.setFileIds(savedFileIds);
        appDoc.setCreatedAt(Instant.now());
        return applicationRepository.save(appDoc);
    }

    // Optionally: method to fetch file by id, delete files, etc.
    public org.springframework.data.mongodb.gridfs.GridFsResource getFileResource(String fileId) throws IllegalArgumentException {
        ObjectId oid = new ObjectId(fileId);
        var file = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(oid)));
        if (file == null) return null;
        return gridFsTemplate.getResource(file);
    }
}
