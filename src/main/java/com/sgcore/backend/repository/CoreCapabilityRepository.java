package com.sgcore.backend.repository;

import com.sgcore.backend.model.CoreCapability;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CoreCapabilityRepository extends MongoRepository<CoreCapability, String> {
    List<CoreCapability> findByServiceId(String serviceId);
}
