package com.anshu.bitebuddy.core.database.model;

import androidx.annotation.Keep;

import java.io.Serializable;

@Keep
public class Food implements Serializable {
    private String name;
    private String id;
    private int price;
    private String description;
    private String image;
    private String category;
    private int rating;
    private int quantity = 1; // Add this line in the fields section

    private String path;

    private Long created;


    public Food() {
    }

    public Food(String name, int price, String description, String image, String category, int rating, String path) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.category = category;
        this.rating = rating;
        this.path = path;
        this.created = System.currentTimeMillis();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getPath() {
        return path;
    }

    public Long getCreated() {
        return created;
    }

    public void setPath(String path) {
        this.path = path;
    }
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
