package com.example.lsd.Controllers.ServiceSeeker;


import com.example.lsd.DBUtils.DBUtils;
import com.example.lsd.Models.Booking;
import com.example.lsd.Views.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import java.util.Comparator;
import java.util.stream.Collectors;

import java.net.URL;
import java.util.ResourceBundle;

public class BookingsController implements Initializable {

    private ObservableList<Booking> addBookings;

    @FXML
    private GridPane bookingGridPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void onBookingGridBoxDisplay() {
        // n rows each have 1 column

        int row = 0;
        int column = 0;

        try {
            bookingGridPane.getColumnConstraints().clear();
            bookingGridPane.getRowConstraints().clear();
            bookingGridPane.getChildren().clear();

            for (int i = 0; i < addBookings.size(); i++) {

                if(column == 1) {
                    column = 0;
                    row++;
                }

                StackPane pane = ViewFactory.getInstance().showBookingGridView(addBookings.get(i), (i + 1));

                bookingGridPane.add(pane, column++, row);
                bookingGridPane.setMargin(pane, new Insets(8));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setUsername(String username) {
        this.addBookings = DBUtils.getBookingsForSeeker(username);
        sortBookings();

        if(addBookings != null) {
            onBookingGridBoxDisplay();
        }
    }

    // Sort the Bookings based on pending and then the most recent ones
    private void sortBookings() {
        // Assuming "pending" is the status to prioritize
        Comparator<Booking> byStatus = Comparator.comparing(booking -> !booking.getStatus().equalsIgnoreCase("pending"));
        Comparator<Booking> byDate = Comparator.comparing(Booking::getBookingDate).reversed();

        this.addBookings = this.addBookings.stream()
                .sorted(byStatus.thenComparing(byDate))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }
}
