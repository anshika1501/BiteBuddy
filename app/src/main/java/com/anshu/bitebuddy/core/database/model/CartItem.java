package com.anshu.bitebuddy.core.database.model;

public class CartItem {
    private String name;
    private int quantity;
    private double price;
    private String imageUrl; //

    public CartItem() {
        // Required for Firestore automatic deserialization
    }

    public CartItem(String name, int quantity, double price, String imageUrl) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    // Getters
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }

    // Setters
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; } // optional
}
