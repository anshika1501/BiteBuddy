package com.anshu.bitebuddy.core.database.model;

import androidx.annotation.Keep;

import java.io.Serializable;

@Keep
public class AddressModel implements Serializable {
    private String name;
    private String phone;
    private String houseName;

    private String area;

    private String landmark;

    private int postalCode;

    private String state;

    private String path;

    public AddressModel() {
    }

    public AddressModel(String name, String phone, String houseName, String area, String landmark, int postalCode, String state) {
        this.name = name;
        this.phone = phone;
        this.houseName = houseName;
        this.area = area;
        this.landmark = landmark;
        this.postalCode = postalCode;
        this.state = state;
        setPath();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPath() {
        return path;
    }

    public void setPath() {
        var landmark = this.landmark.isBlank() ? "_" : this.landmark;
        this.path = name + phone + houseName + area + landmark + postalCode + state;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
