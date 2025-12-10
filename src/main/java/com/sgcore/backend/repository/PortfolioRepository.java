package com.sgcore.backend.repository;

import com.sgcore.backend.model.Portfolio;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PortfolioRepository extends MongoRepository<Portfolio, String> {
}