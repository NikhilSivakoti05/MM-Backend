package com.sgcore.backend.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document(collection = "products")
public class Product {

    @MongoId               // <-- Correct mapping for MongoDB _id
    private String id;     // <-- Your frontend uses this

    private String name;
    private String description;
    private Double price;

    private List<String> imagesBase64;

    private List<FAQ> faqs;

    public Product() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public List<String> getImagesBase64() { return imagesBase64; }
    public void setImagesBase64(List<String> imagesBase64) { this.imagesBase64 = imagesBase64; }

    public List<FAQ> getFaqs() { return faqs; }
    public void setFaqs(List<FAQ> faqs) { this.faqs = faqs; }
}
