package com.example.lsd.Models;

import java.time.LocalDateTime;

public class Booking {
    private int bookingId;
    private String seekerUsername;
    private String providerUsername;
    private String serviceType;
    private LocalDateTime bookingDate;
    private String status;
    private String description;
    private String bookingImage;

    // Constructors
    // Default constructor
    public Booking() {}

    // Parameterized constructor
    public Booking(int bookingId, String seekerUsername, String providerUsername, LocalDateTime bookingDate,
                   String serviceType, String status, String description, String bookingImage) {
        this.bookingId = bookingId;
        this.seekerUsername = seekerUsername;
        this.providerUsername = providerUsername;
        this.bookingDate = bookingDate;
        this.serviceType = serviceType;
        this.status = status;
        this.description = description;
        this.bookingImage = bookingImage;
    }

    // Getters
    public int getBookingId() {
        return bookingId;
    }

    public String getSeekerUsername() {
        return seekerUsername;
    }

    public String getProviderUsername() {
        return providerUsername;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {return description;}

    public String getBookingImage() {return bookingImage;}

    // Setters

    public void setBookingId(int bookingId) {this.bookingId = bookingId;}

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSeekerUsername(String seekerUsername) {
        this.seekerUsername = seekerUsername;
    }

    public void setProviderUsername(String providerUsername) {
        this.providerUsername = providerUsername;
    }

    public void setBookingImage(String bookingImage) {
        this.bookingImage = bookingImage;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }
}