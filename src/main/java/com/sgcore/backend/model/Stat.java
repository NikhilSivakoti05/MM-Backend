package com.sgcore.backend.model;



import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "stats")
public class Stat {

    @Id
    private String id;
    private String label;
    private int value;  // e.g. 100

    public Stat() {}

    public Stat(String label, int value) {
        this.label = label;
        this.value = value;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public int getValue() { return value; }
    public void setValue(int value) { this.value = value; }
}

