package com.example.lsd.Models;


public class User {
    private String cnic;
    private String name;
    private String dateOfBirth;
    private String gender;
    private int age;

    // Constructors
    public User() {}

    public User(String cnic, String name, String dateOfBirth, String gender, int age) {
        this.cnic = cnic;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.age = age;
    }

    // Setters
    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // Getters
    public String getCnic() {
        return cnic;
    }

    public String getName() {
        return name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }
}
