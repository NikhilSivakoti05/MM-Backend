package com.sgcore.backend.model;



import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "portfolio")
public class Portfolio {

    @Id
    private String id;
    private String title;
    private String description;
    private String category;
    private String imageBase64;

    // --- Constructors ---

    // No-args constructor (Required by Spring Data / Hibernate)
    public Portfolio() {
    }

    // All-args constructor
    public Portfolio(String id, String title, String description, String category, String imageBase64) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.imageBase64 = imageBase64;
    }

    // --- Getters and Setters ---

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    // --- toString() ---
    
    @Override
    public String toString() {
        return "Portfolio{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", imageBase64='" + (imageBase64 != null ? "HAS_DATA" : "NULL") + '\'' + // Good practice to not print huge base64 strings
                '}';
    }
}