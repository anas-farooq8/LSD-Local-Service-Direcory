package com.example.lsd.Controllers.ServiceSeeker;

import com.example.lsd.DBUtils.DBUtils;
import com.example.lsd.Views.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import com.example.lsd.Models.ServiceSeeker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ProfileController implements Initializable {
    private String imagePath;

    @FXML
    private Button profileImageChange_btn;

    @FXML
    private TextField address_fld;

    @FXML
    private TextField age_fld;

    @FXML
    private TextField cnic_fld;

    @FXML
    private DatePicker dob_picker;

    @FXML
    private TextField email_fld;

    @FXML
    private TextField gender_fld;

    @FXML
    private TextField name_fld;

    @FXML
    private Label name_lbl;

    @FXML
    private TextField phone_fld;

    @FXML
    private ImageView profile_image;

    @FXML
    private TextField username_fld;

    @FXML
    private TextField zipcode_fld;

    @FXML
    private Button contactInfoChange_btn;

    @FXML
    private Button saveChanges_btn;

    @FXML
    private Button changePass_btn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Check if the user clicked on the camera button beneath the profile picture
        profileImageChange_btn.setOnAction(e -> {
            try {
                onProfileImageChange();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        contactInfoChange_btn.setOnAction(e -> onContactInfoChange());
        saveChanges_btn.setOnAction(e -> onSaveChanges());
        changePass_btn.setOnAction(e -> onPassChange());
    }

    private void onPassChange() {
        ViewFactory.getInstance().getServiceSeekerSelectedMenu().setValue("Change Password");
    }

    private void onSaveChanges() {
        email_fld.setDisable(true);
        phone_fld.setDisable(true);
        address_fld.setDisable(true);
        zipcode_fld.setDisable(true);

        DBUtils.updateServiceSeekerInfo(username_fld.getText(), imagePath, email_fld.getText(),
                phone_fld.getText(), address_fld.getText(), zipcode_fld.getText());

        saveChanges_btn.setDisable(true);
    }

    private void onContactInfoChange() {
        saveChanges_btn.setDisable(false);
        // Contact Info
        email_fld.setDisable(false);
        phone_fld.setDisable(false);
        address_fld.setDisable(false);
        zipcode_fld.setDisable(false);
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

            saveChanges_btn.setDisable(false);
        }
    }

    public void setServiceSeekerInfo(ServiceSeeker s) {
        String imagePath = s.getImage();
        if(imagePath != null) {
            this.imagePath = imagePath;
            File file = new File(imagePath);
            Image image = new Image(file.toURI().toString());
            profile_image.setImage(image);
        }

        name_lbl.setText(s.getName());
        name_fld.setText(s.getName());
        username_fld.setText(s.getUsername());

        String dateString = s.getDateOfBirth();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateOfBirth = LocalDate.parse(dateString, formatter);
        dob_picker.setValue(dateOfBirth);

        email_fld.setText(s.getEmail());
        phone_fld.setText(s.getPhone());
        cnic_fld.setText(s.getCnic());
        address_fld.setText(s.getAddress());
        zipcode_fld.setText(s.getZipcode());

        int age = s.getAge();
        age_fld.setText(Integer.toString(age));

        String gender = s.getGender();
        gender_fld.setText(gender);
    }

}
