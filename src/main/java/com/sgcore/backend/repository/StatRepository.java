package com.sgcore.backend.repository;



import com.sgcore.backend.model.Stat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatRepository extends MongoRepository<Stat, String> {
}

