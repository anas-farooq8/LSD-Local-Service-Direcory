package com.example.lsd.Controllers.Admin;

import com.example.lsd.Views.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {
    private final String cssPath = getClass().getResource("/Styles/ServiceSeekerMenu.css") != null ?
            getClass().getResource("/Styles/ServiceSeekerMenu.css").toExternalForm() : "";

    @FXML
    private Button dashBoard_btn;

    @FXML
    private Button manage_btn;

    @FXML
    private Button bookings_btn;

    @FXML
    private Button logout_btn;

    @FXML
    private Button readSuggestions_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListener();
    }

    private void addListener() {
        dashBoard_btn.setOnAction(e -> onDashBoard());
        manage_btn.setOnAction(e -> onManage());
        logout_btn.setOnAction(e -> onLogout());
        bookings_btn.setOnAction(e -> onBookings());
        readSuggestions_btn.setOnAction(e -> onReadSuggestions());
    }

    private void onReadSuggestions() {
        // Remove the "selected-border" style from all buttons
        resetButtonStyles();
        // Load the CSS file
        readSuggestions_btn.getStylesheets().add(cssPath);
        // Apply the CSS class to the button
        readSuggestions_btn.getStyleClass().add("selected-border");

        ViewFactory.getInstance().getAdminSelectedMenu().set("Suggestions");
    }

    private void onDashBoard() {
        // Remove the "selected-border" style from all buttons
        resetButtonStyles();
        // Load the CSS file
        dashBoard_btn.getStylesheets().add(cssPath);
        // Apply the CSS class to the button
        dashBoard_btn.getStyleClass().add("selected-border");

        ViewFactory.getInstance().getAdminSelectedMenu().set("DashBoard");
    }

    private void onManage() {
        // Remove the "selected-border" style from all buttons
        resetButtonStyles();
        // Load the CSS file
        manage_btn.getStylesheets().add(cssPath);
        // Apply the CSS class to the button
        manage_btn.getStyleClass().add("selected-border");
        ViewFactory.getInstance().getAdminSelectedMenu().set("Manage");
    }

    private void onBookings() {
        // Remove the "selected-border" style from all buttons
        resetButtonStyles();
        // Load the CSS file
        bookings_btn.getStylesheets().add(cssPath);
        // Apply the CSS class to the button
        bookings_btn.getStyleClass().add("selected-border");
        ViewFactory.getInstance().getAdminSelectedMenu().set("Bookings");
    }

    private void onLogout() {
        // Remove the "selected-border" style from all buttons
        resetButtonStyles();
        ViewFactory.getInstance().getAdminSelectedMenu().set("Logout");
    }

    private void resetButtonStyles() {
        // Remove the "selected-border" style from all buttons
        manage_btn.getStyleClass().remove("selected-border");
        dashBoard_btn.getStyleClass().remove("selected-border");
        bookings_btn.getStyleClass().remove("selected-border");
        logout_btn.getStyleClass().remove("selected-border");
        readSuggestions_btn.getStyleClass().remove("selected-border");
    }
}
