package com.example.lsd.Controllers.Admin;

import com.example.lsd.DBUtils.DBUtils;
import com.example.lsd.Models.Category;
import com.example.lsd.Models.ServiceProvider;
import com.example.lsd.Validator.Validator;
import com.example.lsd.Views.ViewFactory;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collections;
import java.util.stream.IntStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ManageServiceProviderController implements Initializable {

    private int currentServiceProviderInd;
    private String imagePath;
    private final String[] genders = {"Male", "Female"};
    private final String[] status = {"Active", "Inactive"};
    private ObservableList<ServiceProvider> addServiceProviders;
    private SortedList<ServiceProvider> sortedList;

    @FXML
    private FontAwesomeIconView availability_icon;

    // Buttons
    @FXML
    private Button camera_btn;

    @FXML
    private Button add_btn;

    @FXML
    private Button update_btn;

    @FXML
    private Button delete_btn;

    @FXML
    private Button clear_btn;

    @FXML
    private TextField zipCode_fld;

    @FXML
    private ComboBox<String> availability_cBox;


    // Table View
    @FXML
    private TableView<ServiceProvider> serviceProvider_tableView;

    @FXML
    private TableColumn<ServiceProvider, String> username_column;

    @FXML
    private TableColumn<ServiceProvider, String> name_column;

    @FXML
    private TableColumn<ServiceProvider, Integer> age_column;

    @FXML
    private TableColumn<ServiceProvider, String> gender_column;

    @FXML
    private TableColumn<ServiceProvider, String> email_column;

    @FXML
    private TableColumn<ServiceProvider, String> phone_column;

    @FXML
    private TableColumn<ServiceProvider, Double> rating_column;

    @FXML
    private TableColumn<ServiceProvider, String> zipCode_column;

    @FXML
    private TableColumn<ServiceProvider, String> status_column;

    @FXML
    private TableColumn<ServiceProvider, List<String>> category_column;


    // Input Fields
    @FXML
    private TextField search_fld;

    @FXML
    private ImageView profile_imageView;

    @FXML
    private TextField username_fld;

    @FXML
    private TextField name_fld;

    @FXML
    private DatePicker dob_picker;

    @FXML
    private ComboBox<String> gender_Cbox;

    @FXML
    private TextField email_fld;

    @FXML
    private TextField phone_fld;

    @FXML
    private ListView<Category> category_listView;


    private void setServiceProvidersData() {
        addServiceProviders = DBUtils.getAllServiceProviders();

        username_column.setCellValueFactory(new PropertyValueFactory<>("username"));
        name_column.setCellValueFactory(new PropertyValueFactory<>("name"));
        age_column.setCellValueFactory(new PropertyValueFactory<>("age"));
        gender_column.setCellValueFactory(new PropertyValueFactory<>("gender"));
        email_column.setCellValueFactory(new PropertyValueFactory<>("email"));
        phone_column.setCellValueFactory(new PropertyValueFactory<>("phone"));
        rating_column.setCellValueFactory(new PropertyValueFactory<>("rating"));
        zipCode_column.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
        status_column.setCellValueFactory(new PropertyValueFactory<>("status"));
        category_column.setCellValueFactory(new PropertyValueFactory<>("categories"));

        serviceProvider_tableView.setItems(addServiceProviders);
    }


    // Search Functionality
    private void onSearch() {
        FilteredList<ServiceProvider> filteredList = new FilteredList<>(addServiceProviders, e-> true);

        search_fld.textProperty().addListener((Observable, oldValue, newValue) -> filteredList.setPredicate(predicateServiceProviderData -> {
            if(newValue == null || newValue.trim().isEmpty()) {
                return true;
            }
            String searchKey = newValue.toLowerCase();
            if(predicateServiceProviderData.getUsername().toLowerCase().contains(searchKey)) {
                return true;
            } else if(predicateServiceProviderData.getName().toLowerCase().contains(searchKey)) {
                return true;
            } else if(String.valueOf(predicateServiceProviderData.getAge()).equals(searchKey)) {
                return true;
            } else if(predicateServiceProviderData.getGender().toLowerCase().equals(searchKey)) {
                return true;
            } else if(String.valueOf(predicateServiceProviderData.getRating()).contains(searchKey)) {
                return true;
            } else return predicateServiceProviderData.getCategories()
                    .stream()
                    .map(Category::getName)
                    .map(String::toLowerCase)
                    .anyMatch(categoryName -> categoryName.contains(searchKey.toLowerCase()));
        }));

        sortedList =  new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(serviceProvider_tableView.comparatorProperty());
        serviceProvider_tableView.setItems(sortedList);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.currentServiceProviderInd = -1;
        gender_Cbox.setItems(genderBox());
        availability_cBox.setItems(statusBox());

        // Set the items in the ListView
        category_listView.setCellFactory(CheckBoxListCell.forListView(Category::selectedProperty));
        // Set the selection mode to single (can be extended or none as well)
        category_listView.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.SINGLE);
        // Loading the Categories
        category_listView.setItems(loadCategories());


        // Loads all the Service Providers in to the table
        setServiceProvidersData();

        serviceProvider_tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Check for single click
                onServiceProviderSelect();
            } else if(event.getClickCount() == 2) {
                // Passing the selected Service Provider Instance
                ServiceProvider serviceProvider = serviceProvider_tableView.getSelectionModel().getSelectedItem();
                if(serviceProvider != null)
                    ViewFactory.getInstance().showServiceProvideProfileView(serviceProvider);
            }
        });

        // Check if the user clicked on the camera button beneath the profile picture
        camera_btn.setOnAction(e -> {
            try {
                onProfileImage();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });


        onSearch();
        add_btn.setOnAction(e -> onChange("Add"));
        update_btn.setOnAction(e -> onChange("Update"));
        delete_btn.setOnAction(e -> onDelete());
        clear_btn.setOnAction(e -> onClear());

        availability_cBox.setOnAction(e -> onStatusSelect());

    }

    private void onStatusSelect() {
        if(availability_cBox.getValue().isEmpty() || availability_cBox.getValue().equals("Inactive"))
            availability_icon.setGlyphName("TOGGLE_OFF");
        else
            availability_icon.setGlyphName("TOGGLE_ON");
    }

    private ObservableList<Category> loadCategories() {
        return FXCollections.observableArrayList(DBUtils.loadAllCategories());
    }

    private ObservableList<String> genderBox() {
        List<String> gendersList = new ArrayList<>();

        Collections.addAll(gendersList, genders);
        return FXCollections.observableArrayList(gendersList);
    }

    private ObservableList<String> statusBox() {
        List<String> statusList = new ArrayList<>();

        Collections.addAll(statusList, status);
        return FXCollections.observableArrayList(statusList);
    }

    // Get the List of Selected Categories
    private List<Category> getSelectedCategories() {
        List<Category> selectedCategories = new ArrayList<>();
        for (Category category : category_listView.getItems()) {
            if (category.isSelected()) {
                selectedCategories.add(category);
            }
        }
        return selectedCategories;
    }

    private void onServiceProviderSelect() {
        currentServiceProviderInd = serviceProvider_tableView.getSelectionModel().getSelectedIndex();
        if(currentServiceProviderInd < 0)  return;

        ServiceProvider serviceProvider = serviceProvider_tableView.getSelectionModel().getSelectedItem();

        username_fld.setText(serviceProvider.getUsername());
        name_fld.setText(serviceProvider.getName());

        String dateString = serviceProvider.getDob();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateOfBirth = LocalDate.parse(dateString, formatter);
        dob_picker.setValue(dateOfBirth);

        // gender
        gender_Cbox.setValue(serviceProvider.getGender());


        email_fld.setText(serviceProvider.getEmail());
        phone_fld.setText(serviceProvider.getPhone());
        zipCode_fld.setText(serviceProvider.getZipCode());
        availability_cBox.setValue(serviceProvider.getStatus());

        // Categories ListView
        List<Category> workingCategories = DBUtils.getServiceProviderCategories(username_fld.getText());

        for (Category category : category_listView.getItems()) {
            // Check if the workingCategories list contains a Category with the same name
            boolean isSelected = workingCategories.stream()
                    .anyMatch(workingCategory -> workingCategory.getName().equals(category.getName()));
            category.setSelected(isSelected);
        }


        String imagePath = serviceProvider.getImage();
        if(!imagePath.trim().isEmpty()) {
            this.imagePath = imagePath;
            File file = new File(imagePath);
            Image image = new Image(file.toURI().toString());
            profile_imageView.setImage(image);
        }

        add_btn.setDisable(true);
        delete_btn.setDisable(false);
        update_btn.setDisable(false);
        username_fld.setDisable(true);
    }


    private void onProfileImage() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        // Set the initial directory
        File initialDirectory = ViewFactory.getInitialDirectory();
        fileChooser.setInitialDirectory(initialDirectory);

        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            // Load the selected image
            Image image = new Image(new FileInputStream(file));
            profile_imageView.setImage(image);
            imagePath = file.getPath();
        }
    }

    private void onChange(String operation) {
        String username = username_fld.getText();
        String name = name_fld.getText();
        String status = availability_cBox.getValue();
        LocalDate selectedDate = dob_picker.getValue();
        String gender = gender_Cbox.getValue();
        String email = email_fld.getText();
        String phone = phone_fld.getText();
        String zipCode = zipCode_fld.getText();

        String formattedDateOfBirth;

        // Validate username
        if (!Validator.isValidUsername(username)) {
            Validator.showErrorAlert("Invalid Username Format", "Please enter a valid username.");
            return;
        }

        // Name Can't be null
        if(name.trim().isEmpty()) return;

        // Validate gender
        if(gender == null) {
            Validator.showErrorAlert("No gender Selected", "Please select a gender.");
            return;
        }

        // Validate availability
        if(status == null) {
            Validator.showErrorAlert("No Status Selected", "Please select a Status.");
            return;
        }

        // Check if a date is selected
        if (selectedDate == null) {
            Validator.showErrorAlert("No Date Selected", "Please select Date of Birth.");
            return;
        } else {
            // Format the selected date using DateTimeFormatter
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            formattedDateOfBirth = selectedDate.format(formatter);
        }

        // Validation for email, phone, address
        if(!Validator.validateEmail(email)) return;
        if(!Validator.validatePhone(phone)) return;
        if(!Validator.validateZipCode(zipCode)) return;

        // Validate image
        if(imagePath == null) {
            Validator.showErrorAlert("No Image Inserted", "Please insert a Image.");
            return;
        }

        List<Category> selectedCategories = getSelectedCategories();
        if(!Validator.validateCategories(selectedCategories)) return;

        ServiceProvider newServiceProvider = new ServiceProvider();
        newServiceProvider.setUsername(username);
        newServiceProvider.setName(name);
        newServiceProvider.setStatus(status);
        newServiceProvider.setDob(formattedDateOfBirth);
        newServiceProvider.setAge(DBUtils.CalculateAge(formattedDateOfBirth));
        newServiceProvider.setGender(gender);

        newServiceProvider.setEmail(email);
        newServiceProvider.setPhone(phone);
        newServiceProvider.setZipCode(zipCode);
        newServiceProvider.setCategories(selectedCategories);

        if(operation.equals("Add")) {
            newServiceProvider.setRating(0.0);
            // Get the current date
            LocalDate currentDate = LocalDate.now();
            // Create a formatter
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            // Format the current date
            String formattedDate = currentDate.format(formatter);
            newServiceProvider.setJoiningDate(formattedDate);
        }

        newServiceProvider.setCategories(selectedCategories);
        newServiceProvider.setImage(imagePath);

        if(operation.equals("Add")) {
            // Inserting the Service
            if(DBUtils.insertServiceProvider(newServiceProvider)) {
                DBUtils.insertServiceProviderCategories(username, newServiceProvider.getCategories());
                // Successfully created (Alert Box)
                Validator.showInformationAlert("Added Successfully", "The Service Provider has been added Successfully.");
                addServiceProviders.add(newServiceProvider);
            } else {
                Validator.showErrorAlert("Error in Insertion", "There seems to be an error while Inserting.");
            }
        } else {
            if(DBUtils.updateServiceProvider(newServiceProvider)) {
                DBUtils.deleteServiceProviderCategories(username);
                DBUtils.insertServiceProviderCategories(username, newServiceProvider.getCategories());

                // Updating in the list
                if (sortedList != null) {
                    final String user = sortedList.get(currentServiceProviderInd).getUsername();
                    // Find the index of the serviceProvider in addServiceProviders list
                    int indexInAddServiceProviders = IntStream.range(0, addServiceProviders.size())
                            .filter(i -> addServiceProviders.get(i).getUsername().equals(user))
                            .findFirst()
                            .orElse(-1);

                    // If found, remove the old and insert the new ServiceProvider at the same index
                    if (indexInAddServiceProviders != -1) {
                        addServiceProviders.set(indexInAddServiceProviders, newServiceProvider);
                    }
                } else {
                    // Remove the old and insert the new ServiceProvider at the currentServiceProviderInd
                    addServiceProviders.set(currentServiceProviderInd, newServiceProvider);
                }


                Validator.showInformationAlert("Updated Successfully", "The Service Provider has been Updated Successfully.");
            } else {
                Validator.showErrorAlert("Error in Updating", "There seems to be an error while Updating.");
            }
        }
        onClear();
    }

    private void onDelete() {
        String username = username_fld.getText();

        if(DBUtils.deleteServiceProvider(username)) {
            addServiceProviders.removeIf(serviceProvider -> serviceProvider.getUsername().equals(username));
            Validator.showInformationAlert("Deleted Successfully", "The Service Provider has been deleted Successfully.");
            onClear();
            delete_btn.setDisable(true);
        } else {
            Validator.showErrorAlert("Error in Deletion", "There seems to be an error while Deletion.");
        }
    }

    private void onClear() {
        add_btn.setDisable(false);
        update_btn.setDisable(true);
        delete_btn.setDisable(true);
        username_fld.setDisable(false);

        username_fld.clear();
        name_fld.clear();
        dob_picker.setValue(null);
        gender_Cbox.setValue("");
        // Set prompt text for ComboBox
        gender_Cbox.setPromptText("Gender");
        email_fld.clear();
        phone_fld.clear();
        imagePath = null;
        profile_imageView.setImage(null);
        zipCode_fld.setText(null);
        availability_cBox.setValue("");

        // Reset all checkboxes in the category list view
        for (Category category : category_listView.getItems()) {
            category.setSelected(false);
        }
    }

}
