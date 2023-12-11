package com.example.lsd.Validator;

import com.example.lsd.Models.Category;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.List;
import java.util.Optional;

public class Validator {

    // Alert Box
    public static void showErrorAlert(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        // Show the alert
        alert.showAndWait();
    }

    // Information Box
    public static void showInformationAlert(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        // Show the alert
        alert.showAndWait();
    }

    // Confirmation Box
    public static boolean showConfirmationAlert(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);

        Optional<ButtonType> optionalButtonType = alert.showAndWait();
        return optionalButtonType.get().equals(ButtonType.OK);
    }


    // Validations
    public static boolean validateEmail(String email) {
        if (email != null && !email.isEmpty() && email.matches(".*@.*\\.com$")) {
            return true;
        } else {
            showErrorAlert("InValid Email", "Please enter a valid email address ending with '.com'.");
            return false;
        }
    }

    public static boolean validatePhone(String phone) {
        if (phone != null && !phone.isEmpty() && phone.matches("\\d{10}")) {
            return true;
        } else {
            showErrorAlert("InValid Phone No.", "Please enter a valid 10-digit phone number.");
            return false;
        }
    }

    public static boolean validateZipCode(String zipCode) {
        if (zipCode != null && !zipCode.isEmpty() && zipCode.matches("\\d{5}")) {
            return true;
        } else {
            showErrorAlert("InValid Zip Code", "Please enter a valid 5-digit zip code.");
            return false;
        }
    }

    public static boolean validateAddress(String address) {
        if (address != null && !address.isEmpty()) {
            return true;
        } else {
            showErrorAlert("InValid Address", "Please enter a valid address.");
            return false;
        }
    }


    public static boolean validateCategories(List<Category> selectedCategories) {
        if (selectedCategories.isEmpty()) {
            showErrorAlert("Selection Error", "You must select at least one category.");
            return false;
        } else if (selectedCategories.size() > 3) {
            showErrorAlert("Selection Error", "You can select no more than 3 categories.");
            return false;
        }
        return true;
    }


    // Validate username
    public static boolean isValidUsername(String username) {
        // Check for null or empty string
        if (username == null || username.trim().isEmpty()) return false;

        // Check length
        if (username.length() < 4 || username.length() > 20) return false;

        // Check for special characters
        return username.matches(".*[@#$%^&!*()_].*");
    }

    // Validate password
    public static boolean isValidPassword(String password) {
        // Check for null or empty string
        if (password == null || password.trim().isEmpty()) {
            return false;
        }

        // Check length
        return (password.length() >= 8 && password.length() <= 30);
    }
}
