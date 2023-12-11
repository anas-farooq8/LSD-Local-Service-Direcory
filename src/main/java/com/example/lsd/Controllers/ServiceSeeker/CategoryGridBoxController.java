package com.example.lsd.Controllers.ServiceSeeker;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class CategoryGridBoxController implements Initializable {

    private String imagePath;
    private String serviceTypeName;

    @FXML
    private ImageView iconImage;

    @FXML
    private Label serviceType;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        serviceType.setText(serviceTypeName.replace(" ", "\n"));

        if (imagePath != null && !imagePath.isEmpty()) {
            File file = new File(imagePath);
            Image image = new Image(file.toURI().toString());
            iconImage.setImage(image);
        }

    }

    public void setServiceType(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


}
