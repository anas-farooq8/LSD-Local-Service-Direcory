package com.example.lsd.Models;

import java.util.List;

public class ServiceProvider {
    private String username;
    private String name;
    // Active / Inactive
    private String status;
    private String dob;
    private int age;
    private String gender;
    private String email;
    private String phone;
    private String zipCode;

    // Total no. of bookings (cancelled, done, pending)
    // no. of reviews given, (review calculation on the basis of avg)
    private String joiningDate;

    private Double rating;
    private List<Category> categories;
    private String image;


    // Constructor, getters, and setters
    public ServiceProvider() {}
    public ServiceProvider(String username, String name, String status, String dob, int age, String gender, String email, String phone, String zipCode, String joiningDate, Double rating, List<Category> categories, String image) {
        this.username = username;
        this.name = name;
        this.status = status;
        this.dob = dob;
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.zipCode = zipCode;
        this.joiningDate = joiningDate;
        this.rating = rating;
        this.categories = categories;
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}

    public String getDob() {return dob;}

    public void setDob(String dob) {this.dob = dob;}

    public int getAge() {return age;}

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {return gender;}

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getZipCode() {return zipCode;}

    public void setZipCode(String zipCode) {this.zipCode = zipCode;}

    public String getJoiningDate() {return joiningDate;}

    public void setJoiningDate(String joiningDate) {this.joiningDate = joiningDate;}

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
