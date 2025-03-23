package com.anshu.bitebuddy.core.database.model;

public class User {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String profilePic;
    private String uid;
    private Long createdAt;

    public User() {
    }

    public User(String name, String email, String phone, String address, String profilePic, String uid) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.profilePic = profilePic;
        this.uid = uid;
        this.createdAt = System.currentTimeMillis();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Long getCreatedAt() {
        return createdAt;
    }
}
