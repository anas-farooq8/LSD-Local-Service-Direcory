package com.example.lsd.Controllers.ServiceSeeker;

import com.example.lsd.DBUtils.DBUtils;
import com.example.lsd.Models.Booking;
import com.example.lsd.Validator.Validator;
import com.example.lsd.Views.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class BookingGridBoxController implements Initializable {

    private int num;
    private Booking booking;
    private final String[] status = {"Pending", "Cancelled", "Completed"};

    @FXML
    private Label number;

    @FXML
    private Label provider_username;

    @FXML
    private TextField scheduled;

    @FXML
    private Label seeker_username;

    @FXML
    private Label serviceType;

    @FXML
    private ComboBox<String> statusCbox;

    @FXML
    private StackPane viewStackPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        number.setText(String.valueOf(num));
        seeker_username.setText(booking.getSeekerUsername());
        provider_username.setText(booking.getProviderUsername());
        serviceType.setText(booking.getServiceType());

        statusCbox.setItems(setStatus());
        String bookingStatus = booking.getStatus();

        if(bookingStatus.equals("Completed") || bookingStatus.equals("Cancelled")){
            statusCbox.setDisable(true);
        }
            statusCbox.setValue(booking.getStatus());

        LocalDateTime bookingDate = booking.getBookingDate();
        String formattedDate = bookingDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        scheduled.setText(formattedDate);

        statusCbox.setOnAction(e -> onStatusChange());

        viewStackPane.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Check for single click
                onViewBooking();
            }
        });

    }

    // View the Booking Details
    private void onViewBooking() {
        // View the selected booking
        ViewFactory.getInstance().showBookingView(booking);
    }

    // Checking if the seeker wants to complete the booking
    private void onStatusChange() {
        String newStatusVal = statusCbox.getValue();

        if(newStatusVal.equals("Cancelled")) {
            if(Validator.showConfirmationAlert("Cancelling Booking", "Are you sure you want to Cancel the Booking.")) {
                statusCbox.setDisable(true);
                if(DBUtils.updateBookingStatus(booking.getBookingId(), newStatusVal)){
                    Validator.showInformationAlert("Booking Cancelled", "The Booking has been Successfully Cancelled.");
                } else {
                    Validator.showErrorAlert("Error in Cancellation", "There seems to be an Error while Cancelling the Booking");
                }
            } else {
                statusCbox.setValue(booking.getStatus());
            }
        } else if(newStatusVal.equals("Completed")) {
            if(DBUtils.updateBookingStatus(booking.getBookingId(), newStatusVal)){
                onRatingView();
                statusCbox.setDisable(true);
            } else {
                Validator.showErrorAlert("Error in Completion", "There seems to be an Error while Completing the Booking");
            }
        }
    }

    // Opens the Rating View on completion of a booking.
    private void onRatingView() {
        ViewFactory.getInstance().showRatingView(booking);
    }


    // Filling the Combo Box
    private ObservableList<String> setStatus() {
        List<String> statusList = new ArrayList<>();

        Collections.addAll(statusList, status);
        return FXCollections.observableArrayList(statusList);
    }


    public void setBooking(Booking booking, int num) {
        this.booking = booking;
        this.num = num;
    }
}
