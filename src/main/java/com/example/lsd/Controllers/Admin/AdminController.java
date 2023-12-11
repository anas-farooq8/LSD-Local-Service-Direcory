package com.example.lsd.Controllers.Admin;


import com.example.lsd.Validator.Validator;
import com.example.lsd.Views.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;


import java.net.URL;
import java.util.ResourceBundle;


public class AdminController implements Initializable {
    @FXML
    private BorderPane admin_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ViewFactory.getInstance().getAdminSelectedMenu().addListener((observableValue, oldVal, newVal) -> {
            switch(newVal) {
                case "Manage":
                    admin_parent.setCenter(ViewFactory.getInstance().getAdminManageServiceProviderView());
                    break;
                case "Bookings":
                    admin_parent.setCenter(ViewFactory.getInstance().getAdminBookingsView());
                    break;
                case "Logout":
                    if(!Validator.showConfirmationAlert("Logging Out", "Are you sure You want to Logout!"))
                        ViewFactory.getInstance().getAdminSelectedMenu().setValue("DashBoard");
                    else System.exit(0);
                    break;
                case "Suggestions":
                    admin_parent.setCenter(ViewFactory.getInstance().getReadSuggestionsView());
                    break;
                    // By default Dashboard View
                default:
                    admin_parent.setCenter(ViewFactory.getInstance().getAdminDashBoardView());
                    break;
            }
        });
    }

}

