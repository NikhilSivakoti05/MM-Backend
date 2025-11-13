package com.sgcore.backend.repository;


import com.sgcore.backend.model.ApplicationDoc;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApplicationRepository extends MongoRepository<ApplicationDoc, String> {
    // add queries if needed
}
