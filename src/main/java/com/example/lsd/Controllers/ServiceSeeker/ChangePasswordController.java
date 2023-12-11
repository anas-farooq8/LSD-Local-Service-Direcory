package com.example.lsd.Controllers.ServiceSeeker;

import com.example.lsd.DBUtils.DBUtils;
import com.example.lsd.Validator.Validator;
import com.example.lsd.Views.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangePasswordController implements Initializable {
    private String username;

    @FXML
    private Button cancel_btn;

    @FXML
    private Button changePassword_btn;

    @FXML
    private PasswordField confirmPassword_fld;

    @FXML
    private PasswordField newPassword_fld;

    @FXML
    private PasswordField oldPassword_fld;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Change Password Controller Called!");

        cancel_btn.setOnAction(e -> onCancel());
        changePassword_btn.setOnAction(e -> onChangePassword());
    }

    private void onChangePassword() {
        String oldPassword = oldPassword_fld.getText();
        String newPassword = newPassword_fld.getText();
        String confirmPassword = confirmPassword_fld.getText();

        System.out.println(username);
        if (Validator.isValidPassword(oldPassword) && DBUtils.oldPasswordCorrect(username, oldPassword)) {
            // Validate password
            if (!Validator.isValidPassword(newPassword)) {
                Validator.showErrorAlert("Invalid Password Format", "Password must be between 8-30 characters.");
            } else {
                // Confirm password
                if (!newPassword.equals(confirmPassword)) {
                    Validator.showErrorAlert("Password Mismatch", "Passwords do not match.");
                } else {
                    // Means Everything is correct.
                    DBUtils.updatePassword(username, newPassword);
                    Validator.showInformationAlert("Password Changed", "Your Account's Password has been Changed Successfully!");
                    onCancel();
                }
            }

        } else {
            Validator.showErrorAlert("Wrong Old Password", "Old Password is Incorrect.");
        }
    }


    private void onCancel() {
        ViewFactory.getInstance().getServiceSeekerSelectedMenu().setValue("Cancel Change Password");
        Stage stage = (Stage) cancel_btn.getScene().getWindow();
        ViewFactory.getInstance().closeStage(stage);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
