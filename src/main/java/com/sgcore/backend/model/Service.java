package com.sgcore.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document(collection = "services")
public class Service {

    @Id
    private String id;

    private String title;
    private String description;

    private List<String> imageUrls; // ✅ IMAGEKIT URLS

    private String category;
    private List<FAQ> faqs;
    private List<ClientFeedback> feedbacks;

    private Instant createdAt = Instant.now();

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public String getCategory() {
        return category;
    }

    public List<FAQ> getFaqs() {
        return faqs;
    }

    public List<ClientFeedback> getFeedbacks() {
        return feedbacks;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setFaqs(List<FAQ> faqs) {
        this.faqs = faqs;
    }

    public void setFeedbacks(List<ClientFeedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
