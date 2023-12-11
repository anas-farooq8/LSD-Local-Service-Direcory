package com.example.lsd.Controllers;

import com.example.lsd.DBUtils.DBUtils;
import com.example.lsd.Views.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    private static final String adminUsername = "admin";
    private static final String adminPassword = "admin123";

    @FXML
    private Label error_lbl;

    @FXML
    private TextField username_fld;

    @FXML
    private PasswordField password_fld;

    @FXML
    private Button showPass_btn;

    @FXML
    private FontAwesomeIconView icon;

    @FXML
    private Button login_btn;

    @FXML
    private Button signup_btn;

    private String errorMessage = "";

    // For shifting between the password and a text field
    TextField plainTextField;
    private boolean isPassVisible = false;

    // It is called after all @FXML annotated members have been injected
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // On Action on clicking the Login Button
        //login_btn.setOnAction(e->isFieldFull());
        login_btn.setOnAction(e -> onLogin());

        HBox parent = (HBox) password_fld.getParent();
        plainTextField = new TextField();
        plainTextField.getStyleClass().add("focused-text-input");

        // For Synchronizing Text and Password Fields
        plainTextField.textProperty().addListener((observable, oldValue, newValue) -> password_fld.setText(newValue));
        password_fld.textProperty().addListener((observable, oldValue, newValue) -> plainTextField.setText(newValue));

        // On Action on clicking the Eye Button
        showPass_btn.setOnAction(e -> togglePasswordVisibility(parent));


        // Signup page
        signup_btn.setOnAction(e -> onSignup());
    }

    // This functions checks if the username and password field is empty
    private boolean isFieldFull(){
        boolean isFilled = true;

        if(username_fld.getText().trim().isEmpty() && (password_fld.getText().trim().isEmpty())){
            errorMessage = "UserName and Password is Empty!";
            error_lbl.setText(errorMessage);
            return false;
        }

        if(username_fld.getText().trim().isEmpty()){
            isFilled = false;
            errorMessage = "UserName is Empty!";
        }

        if( password_fld.getText().trim().isEmpty()){
            isFilled = false;
            errorMessage = "Password is Empty!";
        }

        if(!isFilled)
            error_lbl.setText(errorMessage);
        else
            error_lbl.setText("");

        return isFilled;
    }

    private boolean isServiceSeekerExists() {
        boolean exists = DBUtils.login(username_fld.getText(), password_fld.getText());

        if(!exists) {
            errorMessage = "Wrong UserName or Password!";
            error_lbl.setText(errorMessage);
        }

        return exists;
    }

    // Allows to switch between obscure text
    private void togglePasswordVisibility(HBox parent) {
        if (!isPassVisible) {
            // Create a TextField to display the plain text
            plainTextField.setPrefWidth(password_fld.getWidth());
            plainTextField.setPrefHeight(password_fld.getHeight());
            plainTextField.setFont(password_fld.getFont());
            plainTextField.setAlignment(password_fld.getAlignment());
            plainTextField.setBackground(password_fld.getBackground());
            plainTextField.setPromptText(password_fld.getPromptText());

            // Remove the Password field and Button
            parent.getChildren().removeAll(password_fld, showPass_btn);

            // Add the TextField
            parent.getChildren().addAll(plainTextField, showPass_btn);
            icon.setGlyphName("EYE_SLASH");

            isPassVisible = true;

        }
        else {
            // Remove the TextField and Button
            parent.getChildren().removeIf(node -> node instanceof TextField);
            parent.getChildren().remove(showPass_btn);

            parent.getChildren().addAll(password_fld, showPass_btn);

            icon.setGlyphName("EYE");
            isPassVisible = false;
        }
    }

    private void onLogin() {
        // if fields are full
        if(isFieldFull()) {
            // if the user is an admin
            if(username_fld.getText().equals(adminUsername) && password_fld.getText().equals(adminPassword)) {
                closeLogin();
                ViewFactory.getInstance().showAdminWindow();
            } else { // if the user is a service seeker
                if(isServiceSeekerExists()) {
                    closeLogin();
                    ViewFactory.getInstance().showServiceSeekerWindow(username_fld.getText());
                }
            }
        }
    }

    private void closeLogin() {
        Stage stage = (Stage) login_btn.getScene().getWindow();
        ViewFactory.getInstance().closeStage(stage);
    }


    private void onSignup() {
        Stage stage = (Stage) signup_btn.getScene().getWindow();
        ViewFactory.getInstance().closeStage(stage);
        ViewFactory.getInstance().showSingUpWindow();
    }
}
