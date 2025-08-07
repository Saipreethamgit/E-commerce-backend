package com.gamercein.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "gamercein")
public class Product {

    @Id
    private String id;
    private String name;
    private String category;
    private String description;
    private double price;
    private String image;

    // Constructors
    public Product() {
    }

    public Product(String name, String category, String description, double price, String image) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
