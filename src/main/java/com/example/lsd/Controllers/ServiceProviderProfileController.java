package com.example.lsd.Controllers;

import com.example.lsd.DBUtils.DBUtils;
import com.example.lsd.Models.Category;
import com.example.lsd.Models.ServiceProvider;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.ResourceBundle;

public class ServiceProviderProfileController implements Initializable {
    private ServiceProvider serviceProvider;
    @FXML
    private FontAwesomeIconView availability_icon;

    @FXML
    private TextField availability;

    @FXML
    private TextArea categories;

    @FXML
    private DatePicker dob;

    @FXML
    private TextField email;

    @FXML
    private TextField gender;

    @FXML
    private DatePicker joiningDate;

    @FXML
    private TextField name;

    @FXML
    private TextField noOfReviews;

    @FXML
    private TextField phone;

    @FXML
    private ImageView profileImage;

    @FXML
    private TextField rating;

    @FXML
    private TextField timeServed;

    @FXML
    private TextArea totalBookings;

    @FXML
    private TextField username;

    @FXML
    private TextField zipCode;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String imagePath = serviceProvider.getImage();
        if(imagePath != null || !imagePath.isEmpty()) {
            File file = new File(imagePath);
            Image image = new Image(file.toURI().toString());
            profileImage.setImage(image);
        }

        StringBuilder builder = new StringBuilder();
        for (Category category : serviceProvider.getCategories()) {
            builder.append(category.getName()).append("\n");
        }
        categories.setText(builder.toString());

        username.setText(serviceProvider.getUsername());
        name.setText(serviceProvider.getName());

        String dateString = serviceProvider.getDob();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateOfBirth = LocalDate.parse(dateString, formatter);
        dob.setValue(dateOfBirth);

        gender.setText(serviceProvider.getGender());
        email.setText(serviceProvider.getEmail());
        phone.setText(serviceProvider.getPhone());

        if(serviceProvider.getStatus().isEmpty() || serviceProvider.getStatus().equals("Inactive"))
            availability_icon.setGlyphName("TOGGLE_OFF");
        else
            availability_icon.setGlyphName("TOGGLE_ON");

        availability.setText(serviceProvider.getStatus());

        zipCode.setText(serviceProvider.getZipCode());

        String joiningDateString = serviceProvider.getJoiningDate();
        DateTimeFormatter joinFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate joinDate = LocalDate.parse(joiningDateString, joinFormatter);
        joiningDate.setValue(joinDate);

        timeServed.setText(DBUtils.calculateTimeServed(joiningDateString));

        rating.setText(serviceProvider.getRating().toString());
        noOfReviews.setText(String.valueOf(DBUtils.NumberOfReviewsForServiceProvider(serviceProvider.getUsername())));

        Map<String, Integer> bookingCounts = DBUtils.bookingCountsByStatusForServiceProvider(serviceProvider.getUsername());

        StringBuilder displayText = new StringBuilder();
        for (Map.Entry<String, Integer> entry : bookingCounts.entrySet()) {
            displayText.append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");
        }

        totalBookings.setText(displayText.toString());
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }
}
