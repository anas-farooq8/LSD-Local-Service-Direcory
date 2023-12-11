package com.example.lsd.Controllers.Admin;

import com.example.lsd.DBUtils.DBUtils;
import com.example.lsd.Models.Booking;
import com.example.lsd.Validator.Validator;
import com.example.lsd.Views.ViewFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import javafx.util.Callback;


public class ManageBookingsController implements Initializable {
    private final String cssPath = getClass().getResource("/Styles/ManageServiceProvider.css").toExternalForm();

    private int currentBookingInd;
    private ObservableList<Booking> addBookings;

    // Search
    @FXML
    private TextField search_fld;

    // Table View
    @FXML
    private TableView<Booking> booking_tableView;

    @FXML
    private TableColumn<Booking, Integer> bookingId_column;

    @FXML
    private TableColumn<Booking, String> ssUsername_column;

    @FXML
    private TableColumn<Booking, String> spUsername_column;

    @FXML
    private TableColumn<Booking, String> sType_column;

    @FXML
    private TableColumn<Booking, String> schedule_column;

    @FXML
    private TableColumn<Booking, String> status_column;

    @FXML
    private TableColumn<Booking, String> actions_column;


    private void setBookingData() {
        addBookings = DBUtils.getAllBookings();

        bookingId_column.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        ssUsername_column.setCellValueFactory(new PropertyValueFactory<>("seekerUsername"));
        spUsername_column.setCellValueFactory(new PropertyValueFactory<>("providerUsername"));
        sType_column.setCellValueFactory(new PropertyValueFactory<>("serviceType"));
        // Update this section to format LocalDateTime
        schedule_column.setCellValueFactory(cellData -> {
            LocalDateTime bookingDate = cellData.getValue().getBookingDate();
            String formattedDate = bookingDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            return new SimpleStringProperty(formattedDate);
        });
        status_column.setCellValueFactory(new PropertyValueFactory<>("status"));

        booking_tableView.setItems(addBookings);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.currentBookingInd = -1;

        setBookingData();
        actionButtons();
        onSearch();

    }

    private void actionButtons() {
        Callback<TableColumn<Booking, String>, TableCell<Booking, String>> cellFactory =
                (TableColumn<Booking, String> param) -> new TableCell<>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            Button viewButton = new Button("View");
                            viewButton.getStylesheets().add(cssPath);
                            viewButton.getStyleClass().add("extra-button");

                            Button removeButton = new Button("Delete");
                            removeButton.getStylesheets().add(cssPath);
                            removeButton.getStyleClass().add("extra-button");

                            // Action for the Buttons here
                            viewButton.setOnAction(e -> onView());
                            removeButton.setOnAction(e -> onRemove());

                            HBox manageBtn = new HBox(viewButton, removeButton);
                            manageBtn.setAlignment(Pos.CENTER);
                            manageBtn.setSpacing(5);
                            setGraphic(manageBtn);
                            setText(null);
                        }
                    }
                };

        actions_column.setCellFactory(cellFactory);
        booking_tableView.setItems(addBookings);
    }

    private void onView() {

        // Obtain the current selected booking's id
        currentBookingInd = booking_tableView.getSelectionModel().getSelectedIndex();

        // if not selected than return
        if (currentBookingInd < 0) {
            Validator.showErrorAlert("Not Selected", "Please Select a Booking First.");
            return;
        }

        // Obtain the current selected booking
        Booking selectedBooking = booking_tableView.getSelectionModel().getSelectedItem();

        // View the selected booking
        ViewFactory.getInstance().showBookingView(selectedBooking);
    }


    // on Booking deletion
    private void onRemove() {
        // Obtain the current selected booking's id
        currentBookingInd = booking_tableView.getSelectionModel().getSelectedIndex();

        // if not selected than return
        if (currentBookingInd < 0) {
            Validator.showErrorAlert("Not Selected", "Please Select a Booking First.");
            return;
        }

        // Obtain the current selected booking
        Booking selectedBooking = booking_tableView.getSelectionModel().getSelectedItem();

        // Delete from the database and the list
        if (DBUtils.deleteBooking(selectedBooking.getBookingId())) {
            addBookings.removeIf(booking -> booking.getBookingId() == selectedBooking.getBookingId());
            Validator.showInformationAlert("Deleted Successfully", "The Booking has been deleted Successfully.");
        } else {
            Validator.showErrorAlert("Error in Deletion", "There seems to be an error while Deletion.");
        }
    }

    private void onSearch() {
        FilteredList<Booking> filteredList = new FilteredList<>(addBookings, e -> true);

        search_fld.textProperty().addListener((Observable, oldValue, newValue) -> filteredList.setPredicate(predicateServiceProviderData -> {
            if (newValue == null || newValue.trim().isEmpty()) {
                return true;
            }
            String searchKey = newValue.toLowerCase();
            if (String.valueOf(predicateServiceProviderData.getBookingId()).equals(searchKey)) {
                return true;
            } else if (predicateServiceProviderData.getSeekerUsername().toLowerCase().contains(searchKey)) {
                return true;
            } else if (predicateServiceProviderData.getProviderUsername().contains(searchKey)) {
                return true;
            } else if (predicateServiceProviderData.getServiceType().toLowerCase().contains(searchKey)) {
                return true;
            } else if (predicateServiceProviderData.getStatus().toLowerCase().contains(searchKey)) {
                return true;
            } else return predicateServiceProviderData.getBookingDate()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")).contains(searchKey);
        }));

        SortedList<Booking> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(booking_tableView.comparatorProperty());
        booking_tableView.setItems(sortedList);
    }
}
