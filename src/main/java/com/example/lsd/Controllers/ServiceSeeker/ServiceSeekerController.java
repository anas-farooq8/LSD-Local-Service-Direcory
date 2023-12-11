package com.example.lsd.Controllers.ServiceSeeker;


import com.example.lsd.Validator.Validator;
import com.example.lsd.Views.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ServiceSeekerController implements Initializable {
    @FXML
    private BorderPane serviceSeeker_parent;
    private String username;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        serviceSeeker_parent.setCenter(ViewFactory.getInstance().getServiceSeekerHomeView(username));
        ViewFactory.getInstance().getServiceSeekerSelectedMenu().addListener((observableValue, oldVal, newVal) -> {
            switch(newVal) {
                case "Booking":
                    serviceSeeker_parent.setCenter(ViewFactory.getInstance().getServiceSeekerBookingsView(username));
                    break;
                case "Account":
                    serviceSeeker_parent.setCenter(ViewFactory.getInstance().getServiceSeekerAccountView(username));
                    break;
                case "Profile":
                    serviceSeeker_parent.setCenter(ViewFactory.getInstance().getServiceSeekerProfileView(username));
                    break;
                case "Logout":
                    if(!Validator.showConfirmationAlert("Logging Out", "Are you sure You want to Logout!"))
                        ViewFactory.getInstance().getServiceSeekerSelectedMenu().setValue("Home");
                    else System.exit(0);
                    break;
                case "Change Password":
                    ViewFactory.getInstance().showChangePasswordWindow(username);
                    serviceSeeker_parent.setDisable(true);
                    break;
                case "Cancel Change Password", "Cancel Suggestions":
                    serviceSeeker_parent.setDisable(false);
                    break;
                case "Suggestions":
                    ViewFactory.getInstance().showSuggestionsWindow(username);
                    serviceSeeker_parent.setDisable(true);
                    break;

                default:
                    serviceSeeker_parent.setCenter(ViewFactory.getInstance().getServiceSeekerHomeView(username));
                    break;
            }
        });
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

