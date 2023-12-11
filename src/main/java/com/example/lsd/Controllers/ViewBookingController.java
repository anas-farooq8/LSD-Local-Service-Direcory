package com.example.lsd.Controllers;

import com.example.lsd.DBUtils.DBUtils;
import com.example.lsd.Models.Booking;
import com.example.lsd.Views.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ViewBookingController implements Initializable {
    private Booking booking;

    @FXML
    private ImageView booking_image;

    @FXML
    private Label bookingId;

    @FXML
    private TextArea description;

    @FXML
    private ImageView provider_imageView;

    @FXML
    private TextField provider_username;

    @FXML
    private TextField date;

    @FXML
    private ImageView seeker_imageView;

    @FXML
    private TextField seeker_username;

    @FXML
    private TextField serviceType;

    @FXML
    private TextField status;

    @FXML
    private Label content;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bookingId.setText(String.valueOf(booking.getBookingId()));
        seeker_username.setText(booking.getSeekerUsername());
        provider_username.setText(booking.getProviderUsername());
        serviceType.setText(booking.getServiceType());
        status.setText(booking.getStatus());

        LocalDateTime bookingDate = booking.getBookingDate();
        String formattedDate = bookingDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        date.setText(formattedDate);

        description.setText(booking.getDescription());

        // Booking Image
        String imagePath = booking.getBookingImage();
        if(imagePath != null && !imagePath.isEmpty()) {
            File file = new File(imagePath);
            Image image = new Image(file.toURI().toString());
            booking_image.setImage(image);
            content.setVisible(false);
        }

        // Service Seeker Image
        imagePath = DBUtils.getServiceSeekerImage(booking.getSeekerUsername());
        if(imagePath != null) {
            File file = new File(imagePath);
            Image image = new Image(file.toURI().toString());
            seeker_imageView.setImage(image);
        }

        // Service Provider Image
        imagePath = DBUtils.getServiceProviderImage(booking.getProviderUsername());
        if(imagePath != null) {
            File file = new File(imagePath);
            Image image = new Image(file.toURI().toString());
            provider_imageView.setImage(image);
        }

        // Show Service Provider's Profile details
        provider_imageView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Check for single click
                ViewFactory.getInstance().showServiceProvideProfileView(DBUtils.getSpecificServiceProviders(provider_username.getText()));
            }
        });

        // Show Service Seeker's Profile details
        seeker_imageView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Check for single click
                ViewFactory.getInstance().showServiceProvideProfileView(DBUtils.getSpecificServiceProviders(provider_username.getText()));
            }
        });
    }


    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
