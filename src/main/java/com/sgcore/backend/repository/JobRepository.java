package com.sgcore.backend.repository;

import com.sgcore.backend.model.Job;
import java.util.Optional; // Added this import
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobRepository extends MongoRepository<Job, String> {
    // add custom queries if needed
    Optional<Job> findByJobCode(String jobCode);
}