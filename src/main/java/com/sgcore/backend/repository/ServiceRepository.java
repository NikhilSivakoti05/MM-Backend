package com.sgcore.backend.repository;

import com.sgcore.backend.model.Service;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ServiceRepository extends MongoRepository<Service, String> {
    List<Service> findByCategoryIgnoreCase(String category);
}
