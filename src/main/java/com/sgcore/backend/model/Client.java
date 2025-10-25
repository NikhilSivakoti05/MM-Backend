package com.sgcore.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "clients")
public class Client {

    @Id
    private String id;
    private String name;
    private double lat;
    private double lng;
    private String country;

    public Client() {}

    public Client(String name, double lat, double lng, String country) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.country = country;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getLat() { return lat; }
    public void setLat(double lat) { this.lat = lat; }

    public double getLng() { return lng; }
    public void setLng(double lng) { this.lng = lng; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
}
