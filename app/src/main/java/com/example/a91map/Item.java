package com.example.a91map;

public class Item {
    private int id;
    private String title;
    private String description;
    private String status;
    private String contact;
    private String location;

    public Item(int id, String title, String description, String status, String contact, String location) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.contact = contact;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getContact() {
        return contact;
    }

    public String getLocation() {
        return location;
    }

    // Optional: add setters if needed
    public void setLocation(String location) {
        this.location = location;
    }
}
