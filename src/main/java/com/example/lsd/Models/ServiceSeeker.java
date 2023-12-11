package com.example.lsd.Models;


public class ServiceSeeker {
    private final User u;
    private String image;
    private String username;
    private String email;
    private String phone;
    private String address;
    private String zipcode;
    private String joining_date;


    // Constructors
    public ServiceSeeker() {
        this.u = new User();
    }

    public ServiceSeeker(String username, String name, String dateOfBirth, String gender, int age, String email, String phone,
                         String cnic, String address, String zipcode, String joining_date, String image) {
        this.u = new User();
        this.username = username;
        u.setName(name);
        u.setDateOfBirth(dateOfBirth);
        u.setGender(gender);
        u.setAge(age);
        u.setCnic(cnic);
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.zipcode = zipcode;
        this.joining_date = joining_date;
        this.image = image;
    }


    // Getters and setters

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {return u.getName();}

    public void setName(String name) {u.setName(name);}

    public String getDateOfBirth() {return u.getDateOfBirth();}

    public void setDateOfBirth(String dateOfBirth) {u.setDateOfBirth(dateOfBirth);}

    public int getAge() {
        return u.getAge();
    }

    public void setAge(int age) {
        u.setAge(age);
    }

    public String getGender() {
        return u.getGender();
    }

    public void setGender(String gender) {u.setGender(gender);}

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

    public String getCnic() {return u.getCnic();}

    public void setCnic(String cnic) {u.setCnic(cnic);}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getJoining_date() {return joining_date;}

    public void setJoining_date(String joining_date) {this.joining_date = joining_date;}
}
