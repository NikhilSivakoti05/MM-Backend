package com.sgcore.backend.model;

public class ClientFeedback {
    private String clientName;
    private String feedback;
    private Integer rating;

    // Getters

    public String getClientName() {
        return clientName;
    }

    public String getFeedback() {
        return feedback;
    }

    public Integer getRating() {
        return rating;
    }

    // Setters

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}