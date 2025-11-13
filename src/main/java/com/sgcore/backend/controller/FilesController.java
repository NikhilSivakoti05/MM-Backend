package com.sgcore.backend.controller;

import org.bson.types.ObjectId;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")

public class FilesController {

    private final GridFsTemplate gridFsTemplate;

    public FilesController(GridFsTemplate gridFsTemplate) {
        this.gridFsTemplate = gridFsTemplate;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> download(@PathVariable String id) {
        try {
            var file = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(new ObjectId(id))));
            if (file == null) return ResponseEntity.notFound().build();

            GridFsResource resource = gridFsTemplate.getResource(file);

            return ResponseEntity.ok()
                    .header("Content-Disposition", "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(new InputStreamResource(resource.getInputStream()));

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error reading file");
        }
    }
}
