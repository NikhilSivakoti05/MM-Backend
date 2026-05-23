package com.sgcore.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Document(collection = "applications")
public class ApplicationDoc {

    @Id
    private String id;

    // =========================
    // CUSTOM APPLICATION CODE
    // =========================

    private String applicationCode;

    // =========================
    // JOB INFO
    // =========================

    private String jobId;

    private String jobCode;

    private String jobTitle;

    // =========================
    // APPLICANT INFO
    // =========================

    private String applicantName;

    private String email;

    private String phone;

    // =========================
    // ANSWERS
    // =========================

    private Map<String, Object> answers;

    // =========================
    // FILES
    // =========================

    private List<String> fileUrls;

    private Instant createdAt;

    // =========================
    // GETTERS / SETTERS
    // =========================

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Map<String, Object> getAnswers() {
        return answers;
    }

    public void setAnswers(
            Map<String, Object> answers
    ) {
        this.answers = answers;
    }

    public List<String> getFileUrls() {
        return fileUrls;
    }

    public void setFileUrls(
            List<String> fileUrls
    ) {
        this.fileUrls = fileUrls;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(
            Instant createdAt
    ) {
        this.createdAt = createdAt;
    }
}