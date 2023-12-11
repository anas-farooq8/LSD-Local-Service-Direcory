package com.example.lsd.Controllers.ServiceSeeker;

import com.example.lsd.DBUtils.DBUtils;
import com.example.lsd.Models.Booking;
import com.example.lsd.Validator.Validator;
import com.example.lsd.Views.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import java.text.DecimalFormat;

import java.net.URL;
import java.util.ResourceBundle;

public class RatingController implements Initializable {
    @FXML
    private Slider ratingScore;

    @FXML
    private Button skip_btn;

    @FXML
    private Button submit_btn;

    @FXML
    private Label ratingValue;

    private final int bookingId;

    private final String providerUsername;



    public RatingController(Booking booking) {
        this.bookingId = booking.getBookingId();
        this.providerUsername = booking.getProviderUsername();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        submit_btn.setOnAction(e -> onRatingSubmit());
        skip_btn.setOnAction(e -> onSkip());
        ratingScore.setOnMouseDragged(MouseEvent -> onUpdateRatingScore());
    }

    private void onUpdateRatingScore() {
        // Round off to one decimal place
        double roundedRatingScore = Math.round(ratingScore.getValue() * 10.0) / 10.0;
        ratingValue.setText(String.valueOf(roundedRatingScore));
    }

    private void onSkip() {
        Stage stage = (Stage) submit_btn.getScene().getWindow();
        ViewFactory.getInstance().closeStage(stage);
        Validator.showInformationAlert("Booking Completed", "The Booking has been Successfully Completed.");
    }


    private void onRatingSubmit() {
        double newRating = ratingScore.getValue();

        if(newRating == 0) {
            Validator.showErrorAlert("No Rating selected", "Rating can't be zero.");
            return;
        }


        double currentRating = DBUtils.getServiceProviderRating(providerUsername);
        int totalRatings = DBUtils.NumberOfReviewsForServiceProvider(providerUsername);

        double toUpdateRating = calculateAverageRating(currentRating, newRating, totalRatings);
        DBUtils.updateServiceProviderRating(providerUsername, toUpdateRating);

        DBUtils.insertReview(bookingId, newRating);
        Validator.showInformationAlert("Review Submitted", "Thank you for the Review.");
        onSkip();
    }

    // Calculate the average rating given the current rating and a new rating
    public static double calculateAverageRating(double currentRating, double newRating, int totalRatings) {
        // Assuming totalRatings is the total number of ratings received so far

        // Calculate the new average rating using the current average, new rating, and total ratings
        double newAverageRating = ((currentRating * totalRatings) + newRating) / (totalRatings + 1);

        // Round to one decimal place
        return Math.round(newAverageRating * 10.0) / 10.0;
    }
}
