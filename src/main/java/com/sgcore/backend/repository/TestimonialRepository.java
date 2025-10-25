package com.sgcore.backend.repository;



import com.sgcore.backend.model.Testimonial;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestimonialRepository extends MongoRepository<Testimonial, String> {}

