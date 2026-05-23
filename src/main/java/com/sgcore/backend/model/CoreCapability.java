package com.sgcore.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "core_capabilities")
public class CoreCapability {

    @Id
    private String id;

    private String serviceId;   // 🔗 LINK TO SERVICE
    private String title;
    private String description;

    public String getId() {
        return id;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
