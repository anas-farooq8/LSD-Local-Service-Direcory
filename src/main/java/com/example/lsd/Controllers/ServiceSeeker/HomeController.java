package com.example.lsd.Controllers.ServiceSeeker;

import com.example.lsd.DBUtils.DBUtils;
import com.example.lsd.Models.*;
import com.example.lsd.Validator.Validator;
import com.example.lsd.Views.ViewFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class HomeController implements Initializable {
    private String username;

    private final static String INITIAL_PATH = "D:\\IntellijProjects\\LSD\\src\\main\\resources\\Images\\Icons\\";
    private final static String IMAGE_TYPE = ".png";
    private List<Category> categories;

    private final ObservableList<Category> categoryList = FXCollections.observableArrayList();
    private ObservableList<ServiceProvider> allServiceProviders;
    private SortedList<ServiceProvider> sortedList;

    @FXML
    private GridPane categoryGridPane;

    @FXML
    private GridPane serviceProviderGridPane;

    // Search Field
    @FXML
    private TextField search_fld;

    @FXML
    private AnchorPane bookingAnchor;

    @FXML
    private AnchorPane homeAnchor;

    // Hiring Page
    private static StringProperty selectedServiceProvider;

    @FXML
    private FontAwesomeIconView backIcon;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> timeSlotComboBox;

    @FXML
    private TextField provider_username;

    @FXML
    private ComboBox<String> serviceType;

    @FXML
    private ImageView provider_imageView;

    @FXML
    private ImageView booking_image;

    @FXML
    private Button importImage_btn;

    @FXML
    private TextArea description;

    @FXML
    private Button book_btn;

    @FXML
    private Label categoryPrice;


    private ServiceProvider serviceProvider;
    private String imagePath;


    public static StringProperty getSelectedServiceProvider() {
        return selectedServiceProvider;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.selectedServiceProvider = new SimpleStringProperty("");

        this.categories = DBUtils.loadAllCategories();
        allServiceProviders = DBUtils.getAllServiceProviders();

        onCategoryGridBoxSetData();
        onCategoryGridBoxDisplay();

        if(allServiceProviders != null)
            onServiceProviderGridBoxDisplay();

        onSearch();

        setupDatePicker();
        // Add listener to the DatePicker
        setupDatePickerListener();

        selectedServiceProvider.addListener((observableValue, oldVal, newVal) -> {
            if(!selectedServiceProvider.getValue().equals("Reset")) {
                homeAnchor.setVisible(false);
                bookingAnchor.setVisible(true);
                onBooking();
            }
        });

    }



    // Constructs the Grid Box
    private void onServiceProviderGridBoxDisplay() {
        // n rows each have 4 columns

        int row = 0;
        int column = 0;

        try {
            serviceProviderGridPane.getColumnConstraints().clear();
            serviceProviderGridPane.getRowConstraints().clear();
            serviceProviderGridPane.getChildren().clear();

            for (ServiceProvider serviceProvider : allServiceProviders) {

                if (column == 4) {
                    column = 0;
                    row++;
                }

                StackPane pane = ViewFactory.getInstance().showServiceProviderGridView(serviceProvider);

                serviceProviderGridPane.add(pane, column++, row);
                serviceProviderGridPane.setMargin(pane, new Insets(5));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void onCategoryGridBoxDisplay() {
        // 2 rows each have 9 columns

        int row = 0;
        int column = 0;

        try {
            categoryGridPane.getColumnConstraints().clear();
            categoryGridPane.getRowConstraints().clear();
            categoryGridPane.getChildren().clear();

            for (Category value : categoryList) {

                if (column == 9) {
                    column = 0;
                    row++;
                }

                String categoryName = value.getName();
                StackPane pane = ViewFactory.getInstance().showCategoryGridView(categoryName, INITIAL_PATH + categoryName + IMAGE_TYPE);

                // Handle the click event for the category grid box
                pane.setOnMouseClicked(event -> handleCategoryClick(value));

                categoryGridPane.add(pane, column++, row);
                categoryGridPane.setMargin(pane, new Insets(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Handle the click event for the category grid box
    private void handleCategoryClick(Category selectedCategory) {
        String currentSearchValue = search_fld.getText();
        String categoryValue = selectedCategory.getName();

        // Check if the current search value is the same as the selected category
        if (currentSearchValue.equalsIgnoreCase(categoryValue)) {
            // Reset the search field
            search_fld.clear();
        } else {
            // Set the search field to the selected category
            search_fld.setText(categoryValue);
        }
    }


    // Sets the Data for each Grid Box
    private void onCategoryGridBoxSetData() {
        categoryList.clear();
        categoryList.addAll(categories);
    }

    private void onSearch() {
        FilteredList<ServiceProvider> filteredList = new FilteredList<>(allServiceProviders, e-> true);

        search_fld.textProperty().addListener((Observable, oldValue, newValue) -> {
            filteredList.setPredicate(predicateServiceProviderData -> {
                if(newValue == null || newValue.trim().isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if(predicateServiceProviderData.getName().toLowerCase().contains(searchKey)) {
                    return true;
                } else if(predicateServiceProviderData.getGender().toLowerCase().equals(searchKey)) {
                    return true;
                } else if(predicateServiceProviderData.getZipCode().toLowerCase().contains(searchKey)) {
                    return true;
                } else if(String.valueOf(predicateServiceProviderData.getRating()).contains(searchKey)) {
                    return true;
                } else return predicateServiceProviderData.getCategories()
                        .stream()
                        .map(Category::getName)
                        .map(String::toLowerCase)
                        .anyMatch(categoryName -> categoryName.contains(searchKey.toLowerCase()));
            });

            // Update the grid view based on the filtered list
            // Sort the filtered list by ratings in descending order
            sortedList = new SortedList<>(filteredList, (provider1, provider2) ->
                    Double.compare(provider2.getRating(), provider1.getRating()));
            updateServiceProviderGridView(sortedList);
        });
    }

    // Updates the Grid View
    private void updateServiceProviderGridView(ObservableList<ServiceProvider> filteredProviders) {
        int row = 0;
        int column = 0;
        serviceProviderGridPane.getColumnConstraints().clear();
        serviceProviderGridPane.getRowConstraints().clear();
        serviceProviderGridPane.getChildren().clear();

        for (ServiceProvider provider : filteredProviders) {
            if (column == 4) {
                column = 0;
                row++;
            }

            StackPane pane = ViewFactory.getInstance().showServiceProviderGridView(provider);
            serviceProviderGridPane.add(pane, column++, row);
            serviceProviderGridPane.setMargin(pane, new Insets(5));
        }
    }











    // Bookings View

    private void onBooking() {
        serviceProvider = allServiceProviders.stream()
                .filter(provider -> provider.getUsername().equals(selectedServiceProvider.getValue()))
                .findFirst()
                .orElse(null);

        updateAvailableTimeSlots(LocalDate.now());
        provider_username.setText(serviceProvider.getUsername());
        serviceType.setPromptText("Service Type");
        serviceType.setItems(onServiceType());
        serviceType.setOnAction(e -> onServiceTypeChange());

        String providerImagePath = serviceProvider.getImage();
        if(!providerImagePath.trim().isEmpty()) {
            File file = new File(providerImagePath);
            Image image = new Image(file.toURI().toString());
            provider_imageView.setImage(image);
        }

        // Show Service Provider's Profile details
        provider_imageView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Check for single click
                ViewFactory.getInstance().showServiceProvideProfileView(DBUtils.getSpecificServiceProviders(serviceProvider.getUsername()));
            }
        });

        // Check if the user clicked on the camera button beneath the profile picture
        importImage_btn.setOnAction(e -> {
            try {
                onBookingImageChange();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        book_btn.setOnAction(e -> onAddBooking());

        backIcon.setOnMouseClicked(mouseEvent -> {
            homeAnchor.setVisible(true);
            bookingAnchor.setVisible(false);
            onClear();
            selectedServiceProvider.setValue("Reset");
        });

    }

    private void onAddBooking() {
        if(datePicker.getValue() == null) {
            Validator.showErrorAlert("Date not Selected", "Please Select a Booking date.");
            return;
        }
        if(timeSlotComboBox.getValue() == null) {
            Validator.showErrorAlert("Slot not Selected", "Please Select a Time Slot for the Booking.");
            return;
        }

        if(serviceType.getValue() == null) {
            Validator.showErrorAlert("Service Type not Selected", "Please Select a Service Type for the Booking. ");
            return;
        }

        // Deducting Amount
        ServiceSeeker serviceSeeker = DBUtils.getServiceSeekerInfo(username);
        double amount = DBUtils.getCategoryPiceByName(serviceType.getValue());
        if(DBUtils.deductAmount(serviceSeeker.getCnic(), amount)) {
            Booking booking = new Booking();
            int bookingId = DBUtils.getMaxBookingId() + 1;
            booking.setBookingId(bookingId);
            booking.setSeekerUsername(username);
            booking.setProviderUsername(serviceProvider.getUsername());

            // Extracting the selected date from datePicker
            LocalDate selectedDate = datePicker.getValue();
            // Get the selected time slot from timeSlotComboBox
            String selectedTimeSlot = timeSlotComboBox.getValue();
            // Parse the time from the selected time slot
            String startTime = selectedTimeSlot.split("-")[0];
            // Combine the date and time
            String dateTimeString = selectedDate.toString() + " " + startTime;
            // Parse the combined date and time string
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime bookingDateTime = LocalDateTime.parse(dateTimeString, formatter);
            // Set the booking date
            booking.setBookingDate(bookingDateTime);

            booking.setServiceType(serviceType.getValue());
            booking.setBookingImage(imagePath);
            booking.setDescription(description.getText());
            booking.setStatus("Pending");

            if(!DBUtils.insertBooking(booking)) {
                Validator.showErrorAlert("Booking Failed!", "Booking can't be made for certain reasons. Please Try Again");
            } else {
                Validator.showInformationAlert("Booking Successfully", "The Booking has been made Successfully.");
                Payment payment = new Payment();
                payment.setBookingId(bookingId);
                payment.setAmount(amount);
                payment.setPaymentDate(LocalDateTime.now());
                payment.setPaymentMethod("Card");

                DBUtils.insertPayment(payment);
            }
        }

        onClear();
    }

    // Update Money on Service Type Change
    private void onServiceTypeChange() {
        String serviceTypeName = serviceType.getValue();
        categoryPrice.setText("$ " + String.valueOf(DBUtils.getCategoryPiceByName(serviceTypeName)));
    }

    // Clear the Booking Screen
    private void onClear() {
        datePicker.setValue(null);
        timeSlotComboBox.setValue(null);
        provider_username.setText(null);
        serviceType.setValue(null);
        categoryPrice.setText(null);
        provider_imageView.setImage(null);
        booking_image.setImage(null);
        description.setText(null);
    }

    public void onBookingImageChange() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        // Set the initial directory
        File initialDirectory = ViewFactory.getInitialDirectory();
        fileChooser.setInitialDirectory(initialDirectory);

        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            // Load the selected image
            Image image = new Image(new FileInputStream(file));
            booking_image.setImage(image);
            imagePath = file.getPath();
            importImage_btn.setOpacity(0.2);
        }
    }

    // Sets the service type of service provider
    private ObservableList<String> onServiceType() {
        List<String> servicesList = serviceProvider.getCategories().stream()
                .map(Category::getName)
                .collect(Collectors.toList());

        return FXCollections.observableArrayList(servicesList);
    }

    // Listens to changes in date and updates the time slots
    private void setupDatePickerListener() {
        datePicker.valueProperty().addListener((obs, oldDate, newDate) -> {
            if (newDate != null) {
                updateAvailableTimeSlots(newDate);
            }
        });
    }

    // date picker calls this function
    private void updateAvailableTimeSlots(LocalDate date) {
        List<String> unavailableSlots = DBUtils.getUnavailableSlotsForProvider(serviceProvider.getUsername(), date);
        List<String> allSlots = getAllTimeSlots();
        List<String> availableSlots = new ArrayList<>(allSlots);

        // Convert the unavailable time slots to their range format
        List<String> unavailableSlotRanges = unavailableSlots.stream()
                .map(this::convertToRangeFormat)
                .collect(Collectors.toList());

        // Remove unavailable slot ranges from the list of available slots
        availableSlots.removeAll(unavailableSlotRanges);

        timeSlotComboBox.getItems().clear();
        timeSlotComboBox.getItems().addAll(availableSlots);
    }

    private String convertToRangeFormat(String slotTime) {
        // Assuming slotTime is in format "HH:mm:ss"
        LocalTime startTime = LocalTime.parse(slotTime, DateTimeFormatter.ofPattern("HH:mm:ss"));
        LocalTime endTime = startTime.plusHours(1);
        return formatTimeSlot(startTime, endTime);
    }

    // Setting up the Date (Current Date + 10 days)
    private void setupDatePicker() {
        datePicker.setValue(LocalDate.now()); // Set to current date as default
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now().plusDays(10)) > 0 || date.compareTo(LocalDate.now()) < 0);
            }
        });
        //updateAvailableTimeSlots(LocalDate.now());
    }

    private List<String> getAllTimeSlots() {
        List<String> allSlots = new ArrayList<>();

        // Define time slot intervals
        LocalTime startTimeMorning = LocalTime.of(8, 0); // 8:00 AM
        LocalTime endTimeMorning = LocalTime.of(12, 0); // 12:00 PM
        addTimeSlotsToSlotList(allSlots, startTimeMorning, endTimeMorning);

        LocalTime startTimeNoon = LocalTime.of(14, 0); // 2:00 PM
        LocalTime endTimeNoon = LocalTime.of(18, 0); // 6:00 PM
        addTimeSlotsToSlotList(allSlots, startTimeNoon, endTimeNoon);

        LocalTime startTimeNight = LocalTime.of(20, 0); // 8:00 PM
        LocalTime endTimeNight = LocalTime.of(23, 0); // 11:00 PM
        addTimeSlotsToSlotList(allSlots, startTimeNight, endTimeNight);

        return allSlots;
    }

    private void addTimeSlotsToSlotList(List<String> slotList, LocalTime startTime, LocalTime endTime) {
        while (startTime.isBefore(endTime)) {
            LocalTime slotEnd = startTime.plusHours(1);
            slotList.add(formatTimeSlot(startTime, slotEnd));
            startTime = slotEnd;
        }
    }

    private String formatTimeSlot(LocalTime start, LocalTime end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return start.format(formatter) + "-" + end.format(formatter);
    }


}
