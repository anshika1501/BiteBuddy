package com.anshu.bitebuddy.core.database.model;

import androidx.annotation.Keep;

import java.io.Serializable;

@Keep
public class OrderModel implements Serializable {
    private Food furniture;
    private double price;
    private OrderStates orderState;
    private AddressModel address;
    private Long orderedAt;
    private String path;

    public OrderModel(Food food, double price, AddressModel address, Long orderedAt, String path) {
        this.furniture = food;
        this.price = price;
        this.address = address;
        this.orderedAt = orderedAt;
        this.path = path;
        this.orderState = OrderStates.values()[(int) (Math.random() * OrderStates.values().length)];
    }

    public OrderModel() {
    }

    public Food getFurniture() {
        return furniture;
    }

    public void setFurniture(Food furniture) {
        this.furniture = furniture;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public OrderStates getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderStates orderState) {
        this.orderState = orderState;
    }

    public AddressModel getAddress() {
        return address;
    }

    public void setAddress(AddressModel address) {
        this.address = address;
    }

    public Long getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(Long orderedAt) {
        this.orderedAt = orderedAt;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
