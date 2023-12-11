package com.example.lsd.Controllers.ServiceSeeker;

import com.example.lsd.Views.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class ServiceSeekerMenuController implements Initializable {
    private final String cssPath = getClass().getResource("/Styles/ServiceSeekerMenu.css").toExternalForm();
    @FXML
    private Button home_btn;

    @FXML
    private Button booking_btn;

    @FXML
    private Button account_btn;

    @FXML
    private Button profile_btn;

    @FXML
    private Button logout_btn;

    @FXML
    private Button suggestions_btn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListener();
    }

    private void addListener() {
        home_btn.setOnAction(e->onHome());
        booking_btn.setOnAction(e -> onBooking());
        account_btn.setOnAction(e -> onAccount());
        profile_btn.setOnAction(e->onProfile());
        suggestions_btn.setOnAction(e -> onSuggestions());
        logout_btn.setOnAction(e->onLogout());
    }

    private void onAccount() {
        // Remove the "selected-border" style from all buttons
        resetButtonStyles();
        // Load the CSS file
        account_btn.getStylesheets().add(cssPath);
        // Apply the CSS class to the button
        account_btn.getStyleClass().add("selected-border");
        ViewFactory.getInstance().getServiceSeekerSelectedMenu().set("Account");
    }

    private void onSuggestions() {
        ViewFactory.getInstance().getServiceSeekerSelectedMenu().set("Suggestions");
    }

    private void onBooking() {
        // Remove the "selected-border" style from all buttons
        resetButtonStyles();
        // Load the CSS file
        booking_btn.getStylesheets().add(cssPath);
        // Apply the CSS class to the button
        booking_btn.getStyleClass().add("selected-border");

        ViewFactory.getInstance().getServiceSeekerSelectedMenu().set("Booking");
    }

    private void onHome() {
        // Remove the "selected-border" style from all buttons
        resetButtonStyles();
        // Load the CSS file
        home_btn.getStylesheets().add(cssPath);
        // Apply the CSS class to the button
        home_btn.getStyleClass().add("selected-border");

        ViewFactory.getInstance().getServiceSeekerSelectedMenu().set("Home");
    }

    private void onProfile() {
        // Remove the "selected-border" style from all buttons
        resetButtonStyles();
        // Load the CSS file
        profile_btn.getStylesheets().add(cssPath);
        // Apply the CSS class to the button
        profile_btn.getStyleClass().add("selected-border");
        ViewFactory.getInstance().getServiceSeekerSelectedMenu().set("Profile");
    }

    private void onLogout() {
        // Remove the "selected-border" style from all buttons
        resetButtonStyles();
        ViewFactory.getInstance().getServiceSeekerSelectedMenu().set("Logout");
    }

    private void resetButtonStyles() {
        // Remove the "selected-border" style from all buttons
        home_btn.getStyleClass().remove("selected-border");
        booking_btn.getStyleClass().remove("selected-border");
        account_btn.getStyleClass().remove("selected-border");
        profile_btn.getStyleClass().remove("selected-border");
        logout_btn.getStyleClass().remove("selected-border");
        suggestions_btn.getStyleClass().remove("selected-border");
    }
}
