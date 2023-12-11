package com.example.lsd.Controllers;

import com.example.lsd.DBUtils.DBUtils;
import com.example.lsd.Models.ServiceSeeker;
import com.example.lsd.Models.User;
import com.example.lsd.Validator.Validator;
import com.example.lsd.Views.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.control.PasswordField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class SignupController implements Initializable {

    private static final Logger logger = Logger.getLogger(ViewFactory.class.getName());
    private String imagePath = "";


    @FXML
    private TextField address_fld;

    @FXML
    private TextField age_fld;

    @FXML
    private Button alreadyHaveAcc_btn;

    @FXML
    private Button backFirst_btn;

    @FXML
    private Button backSecond_btn;

    @FXML
    private TextField cnic_fld;

    @FXML
    private PasswordField cpassword_fld;

    @FXML
    private TextField dob_fld;

    @FXML
    private TextField email_fld;

    @FXML
    private TextField gender_fld;

    @FXML
    private TextField name_fld;

    @FXML
    private Button nextSecond_btn;

    @FXML
    private Button nextThird_btn;

    @FXML
    private PasswordField password_fld;

    @FXML
    private TextField phone_fld;

    @FXML
    private Button profileImageChange_btn;

    @FXML
    private ImageView profile_image;

    @FXML
    private Button signup_btn;

    @FXML
    private TextField username_fld;

    @FXML
    private VBox vBox_first;

    @FXML
    private VBox vBox_second;

    @FXML
    private VBox vBox_third;

    @FXML
    private Button validate_btn;

    @FXML
    private TextField zipCode_fld;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // first vBox
        validate_btn.setOnAction(e -> onValidate());
        nextSecond_btn.setOnAction(e -> onNextSecond());
        alreadyHaveAcc_btn.setOnAction(e -> onAlreadyHaveAccount());

        // second vBox
        profileImageChange_btn.setOnAction(e -> {
            try {
                onProfileImageChange();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        backFirst_btn.setOnAction(e -> onBackFirst());
        nextThird_btn.setOnAction(e -> onNextThird());

        // third vBox
        backSecond_btn.setOnAction(e -> onBackSecond());
        signup_btn.setOnAction(e -> onSignUp());


    }

    public void onProfileImageChange() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        // Set the initial directory
        File initialDirectory = ViewFactory.getInitialDirectory();
        fileChooser.setInitialDirectory(initialDirectory);

        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            // Load the selected image
            Image image = new Image(new FileInputStream(file));
            profile_image.setImage(image);
            imagePath = file.getPath();
        }
    }

    private void onAlreadyHaveAccount() {
        ViewFactory.getInstance().showLoginWindow();
        Stage stage = (Stage) alreadyHaveAcc_btn.getScene().getWindow();
        ViewFactory.getInstance().closeStage(stage);
    }



    private boolean isFieldFull(){
        boolean isFilled = true;

        if(cnic_fld.getText().trim().isEmpty()){
            isFilled = false;
            // Make a beautiful alert box that displays the user the input field is empty
            Validator.showErrorAlert("Input Error", "Please enter a value for CNIC.");
        }

        return isFilled;
    }
    private boolean isUserExist() {
        boolean exists = DBUtils.evcExist(cnic_fld.getText());

        // If he/she has a valid ID card
        if(exists) {
            // check in database of service seeker (already exist)
            exists = DBUtils.isAlreadyExist(cnic_fld.getText());

            if(exists) {
                // AlertBox (Already exist in database)
                Validator.showErrorAlert("Already Exist Error", "User with the CNIC already Exist!");

                return !exists;
            }
        } else {
            // Cnic is invalid
            Validator.showErrorAlert("Invalid CNIC", "The provided CNIC is not valid. Please check and try again.");
            return exists;
        }

        return !exists;
    }

    private void onValidate() {
        if(isFieldFull() && isUserExist()) {
            User newUser = DBUtils.getUserInfo(cnic_fld.getText());
            if(newUser != null) {
              name_fld.setText(newUser.getName());
              dob_fld.setText(newUser.getDateOfBirth());
              gender_fld.setText(newUser.getGender());
              age_fld.setText(String.valueOf(newUser.getAge()));

              nextSecond_btn.setDisable(false);
            } else {
                // Log a warning instead of printing the stack trace
                logger.warning("User Data is null for cnic: " + cnic_fld.getText());
            }
        }
    }


    private void onNextSecond() {
        vBox_first.setVisible(false);
        vBox_second.setVisible(true);
    }

    private void onBackFirst() {
        vBox_second.setVisible(false);
        vBox_first.setVisible(true);
    }

    private void onNextThird() {
        String email = email_fld.getText();
        String phone = phone_fld.getText();
        String zipCode = zipCode_fld.getText();
        String address = address_fld.getText();

        // Validation for email, phone, address, zip code
        if (Validator.validateEmail(email) && Validator.validatePhone(phone) && Validator.validateZipCode(zipCode) && Validator.validateAddress(address)) {
            vBox_second.setVisible(false);
            vBox_third.setVisible(true);
        }
    }

    private void onBackSecond() {
        vBox_third.setVisible(false);
        vBox_second.setVisible(true);
    }

    private void onSignUp() {
        String username = username_fld.getText();
        String password = password_fld.getText();
        String confirmPassword = cpassword_fld.getText();

        // Validate username
        if (!Validator.isValidUsername(username)) {
            Validator.showErrorAlert("Invalid Username Format", "Please enter a valid username.");
            return;
        }

        // Check if username already exists
        if (DBUtils.usernameAlreadyExist(username)) {
            Validator.showErrorAlert("Username Already Exists", "Please choose a different username.");
            return;
        }

        // Validate password
        if (!Validator.isValidPassword(password)) {
            Validator.showErrorAlert("Invalid Password Format", "Password must be between 8-30 characters.");
            return;
        }

        // Confirm password
        if (!password.equals(confirmPassword)) {
            Validator.showErrorAlert("Password Mismatch", "Please confirm your password correctly.");
            return;
        }

        //cnic, name, dob, gender, age
        ServiceSeeker newServiceSeeker = new ServiceSeeker();
        newServiceSeeker.setImage(imagePath);
        newServiceSeeker.setUsername(username);
        newServiceSeeker.setName(name_fld.getText());
        newServiceSeeker.setDateOfBirth(dob_fld.getText());
        newServiceSeeker.setAge(Integer.parseInt(age_fld.getText()));
        newServiceSeeker.setGender(gender_fld.getText());
        // Get the current date
        LocalDate currentDate = LocalDate.now();
        // Create a formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // Format the current date
        String formattedDate = currentDate.format(formatter);
        newServiceSeeker.setJoining_date(formattedDate);
        newServiceSeeker.setEmail(email_fld.getText());
        newServiceSeeker.setPhone(phone_fld.getText());
        newServiceSeeker.setCnic(cnic_fld.getText());
        newServiceSeeker.setAddress(address_fld.getText());
        newServiceSeeker.setZipcode(zipCode_fld.getText());

        DBUtils.insertServiceSeeker(newServiceSeeker);
        DBUtils.insertLoginCredentials(username, password);

        // Successfully created (Alert Box)
        Validator.showInformationAlert("Sign Up Successful", "Account created successfully!\nYou can now log in with your credentials.");


        Stage stage = (Stage) signup_btn.getScene().getWindow();
        ViewFactory.getInstance().closeStage(stage);
        ViewFactory.getInstance().showLoginWindow();

    }

}
