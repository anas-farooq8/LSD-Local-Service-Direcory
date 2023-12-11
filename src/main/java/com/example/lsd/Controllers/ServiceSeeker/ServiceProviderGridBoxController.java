package com.example.lsd.Controllers.ServiceSeeker;

import com.example.lsd.Models.Category;
import com.example.lsd.Models.ServiceProvider;
import com.example.lsd.Views.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ServiceProviderGridBoxController implements Initializable {

    private ServiceProvider serviceProvider;

    @FXML
    private TextArea categories;

    @FXML
    private Button hire_btn;

    @FXML
    private Label name;

    @FXML
    private ImageView profile;

    @FXML
    private Label rating;

    @FXML
    private Button view_btn;

    @FXML
    private Label zipCode;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String imagePath = serviceProvider.getImage();
        if(imagePath != null || !imagePath.isEmpty()) {
            File file = new File(imagePath);
            Image image = new Image(file.toURI().toString());
            profile.setImage(image);
        }

        name.setText(serviceProvider.getName());
        zipCode.setText(serviceProvider.getZipCode());
        rating.setText(String.valueOf(serviceProvider.getRating()));


        StringBuilder builder = new StringBuilder();
        for (Category category : serviceProvider.getCategories()) {
            builder.append(category.getName()).append("\n");
        }

        categories.setText(builder.toString());

        view_btn.setOnAction(e -> onProfileView());
        if(serviceProvider.getStatus().equals("Inactive"))
            hire_btn.setDisable(true);

        hire_btn.setOnAction(e -> onHire());
    }

    private void onHire() {
        HomeController.getSelectedServiceProvider().setValue(serviceProvider.getUsername());
    }

    private void onProfileView() {
        ViewFactory.getInstance().showServiceProvideProfileView(serviceProvider);
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }
}
