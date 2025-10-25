package com.sgcore.backend.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "testimonials")
public class Testimonial {

    @Id
    private String id;
    private String content;
    private String author;
    private String role;

    // Default constructor
    public Testimonial() {}

    // All-args constructor
    public Testimonial(String id, String content, String author, String role) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.role = role;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
