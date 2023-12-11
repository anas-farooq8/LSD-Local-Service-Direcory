package com.example.lsd.Models;

import java.time.LocalDate;

public class Account {
    private int accountId;
    private String userCnic;
    private String accountNumber;
    private String creditCardNumber;
    private LocalDate creditCardExpiry;
    private int cvv;
    private int pin;
    private double balance;

    // Default constructor
    public Account() {
    }

    // Parameterized constructor
    public Account(int accountId, String userCnic, String accountNumber, String creditCardNumber,
                   LocalDate creditCardExpiry, int cvv, int pin, double balance) {
        this.accountId = accountId;
        this.userCnic = userCnic;
        this.accountNumber = accountNumber;
        this.creditCardNumber = creditCardNumber;
        this.creditCardExpiry = creditCardExpiry;
        this.cvv = cvv;
        this.pin = pin;
        this.balance = balance;
    }

    // Getters and Setters
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getUserCnic() {
        return userCnic;
    }

    public void setUserCnic(String userCnic) {
        this.userCnic = userCnic;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public LocalDate getCreditCardExpiry() {
        return creditCardExpiry;
    }

    public void setCreditCardExpiry(LocalDate creditCardExpiry) {
        this.creditCardExpiry = creditCardExpiry;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Optional: toString method for debugging
    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", userCnic='" + userCnic + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", creditCardNumber='" + creditCardNumber + '\'' +
                ", creditCardExpiry=" + creditCardExpiry +
                ", cvv=" + cvv +
                ", pin=" + pin +
                ", balance=" + balance +
                '}';
    }
}
