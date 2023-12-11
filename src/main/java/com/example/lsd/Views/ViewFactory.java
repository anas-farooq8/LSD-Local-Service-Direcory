package com.example.lsd.Views;

import com.example.lsd.Controllers.Admin.AdminController;
import com.example.lsd.Controllers.Admin.ReadSuggestionsGribBoxController;
import com.example.lsd.Controllers.ServiceProviderProfileController;
import com.example.lsd.Controllers.ViewBookingController;
import com.example.lsd.Controllers.ServiceSeeker.*;
import com.example.lsd.DBUtils.DBUtils;
import com.example.lsd.Models.Booking;
import com.example.lsd.Models.ServiceProvider;
import com.example.lsd.Models.ServiceSeeker;
import com.example.lsd.Models.Suggestion;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewFactory {
    // For Exception Handling
    private static final Logger logger = Logger.getLogger(ViewFactory.class.getName());

    // Initial Path for image Opening
    private static final File initialDirectory = new File("D:\\IntellijProjects\\LSD\\src\\main\\resources\\Images");

    // Singleton instance
    private static ViewFactory viewFactory;

    // Service Seeker Views
    private final StringProperty ServiceSeekerSelectedMenu;
    private AnchorPane serviceSeekerHomeView;
    private AnchorPane serviceSeekerBookingsView;
    private AnchorPane serviceSeekerAccountView;
    private AnchorPane serviceSeekerProfileView;


    // Admin Views
    private final StringProperty adminSelectedMenu;
    private AnchorPane adminDashBoardView;
    private AnchorPane adminManageServiceProviderView;
    private AnchorPane adminManageBookingsView;
    private AnchorPane adminReadSuggestionsView;




    private ViewFactory() {
        this.ServiceSeekerSelectedMenu = new SimpleStringProperty("");
        this.adminSelectedMenu = new SimpleStringProperty("");

        // private constructor to prevent instantiation
    }


    // Get instance method
    public static synchronized ViewFactory getInstance() {
        if (viewFactory == null) {
            viewFactory = new ViewFactory();
        }
        return viewFactory;
    }


    // Returns the image path
    public static File getInitialDirectory() {return initialDirectory;}

    // Service Seeker Views
    public StringProperty getServiceSeekerSelectedMenu() {
        return ServiceSeekerSelectedMenu;
    }

    // Home View
    public AnchorPane getServiceSeekerHomeView(String username) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/ServiceSeeker/Home.fxml"));
            serviceSeekerHomeView = loader.load();
            HomeController homeController = loader.getController();

            homeController.setUsername(username);

        } catch (Exception e) {
            // Log the exception instead of printing the stack trace
            logger.log(Level.SEVERE, "An error occurred while loading profile view", e);
        }
        return serviceSeekerHomeView;
    }


    // Bookings View
    public AnchorPane getServiceSeekerBookingsView(String username) {
        // To maintain the Referential Integrity
        // When a Booking is done it's content should be reflected in this view.
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/ServiceSeeker/Bookings.fxml"));
            serviceSeekerBookingsView = loader.load();
            BookingsController bookingsController = loader.getController();

            bookingsController.setUsername(username);
        } catch (Exception e) {
            // Log the exception instead of printing the stack trace
            logger.log(Level.SEVERE, "An error occurred while loading dashboard view", e);
        }

        return serviceSeekerBookingsView;
    }

    // Booking Grid View
    public StackPane showBookingGridView(Booking booking, int ind) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/ServiceSeeker/BookingGridBox.fxml"));
            BookingGridBoxController bookingGridBoxController = new BookingGridBoxController();
            loader.setController(bookingGridBoxController);

            bookingGridBoxController.setBooking(booking, ind);

            return loader.load();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Bookings View
    public AnchorPane getServiceSeekerAccountView(String username) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/ServiceSeeker/Account.fxml"));
            serviceSeekerAccountView = loader.load();
            AccountController accountController = loader.getController();

            accountController.setUsername(username);
        } catch (Exception e) {
            // Log the exception instead of printing the stack trace
            logger.log(Level.SEVERE, "An error occurred while loading dashboard view", e);
        }

        return serviceSeekerAccountView;
    }

    // Profile View
    public AnchorPane getServiceSeekerProfileView(String username) {
        if (serviceSeekerProfileView == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/ServiceSeeker/Profile.fxml"));
                serviceSeekerProfileView = loader.load();
                ProfileController profileController = loader.getController();

                // Check if the result from DBUtils.getServiceSeeker(username) is null
                ServiceSeeker serviceSeeker = DBUtils.getServiceSeekerInfo(username);
                if (serviceSeeker != null) {
                    profileController.setServiceSeekerInfo(serviceSeeker);
                } else {
                    // Log a warning instead of printing the stack trace
                    logger.warning("ServiceSeeker is null for username: " + username);
                }

            } catch (Exception e) {
                // Log the exception instead of printing the stack trace
                logger.log(Level.SEVERE, "An error occurred while loading profile view", e);
            }
        }
        return serviceSeekerProfileView;
    }


    // Change Password View
    public void showChangePasswordWindow(String username) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/ServiceSeeker/ChangePassword.fxml"));
        ChangePasswordController changePasswordController = new ChangePasswordController();
        loader.setController(changePasswordController);

        changePasswordController.setUsername(username);
        createStage(loader);
    }


    // Suggestions View
    public void showSuggestionsWindow(String username) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/ServiceSeeker/Suggestions.fxml"));
        SuggestionsController suggestionsController = new SuggestionsController();
        loader.setController(suggestionsController);

        suggestionsController.setUsername(username);
        createStage(loader);
    }



    public void showServiceSeekerWindow(String username) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/ServiceSeeker/ServiceSeeker.fxml"));

        ServiceSeekerController serviceseekercontroller = new ServiceSeekerController();
        loader.setController(serviceseekercontroller);

        serviceseekercontroller.setUsername(username);
        createStage(loader);
    }




    // Admin views
    public StringProperty getAdminSelectedMenu() {
        return adminSelectedMenu;
    }

    // Admin Dashboard
    public AnchorPane getAdminDashBoardView() {
        // To maintain the Referential Integrity
        // When a service provider is deleted or updated it's content should be reflected in the DashBoard Menu.
        try {
            adminDashBoardView = new FXMLLoader(getClass().getResource("/Fxml/Admin/DashBoard.fxml")).load();
        } catch (Exception e) {
            // Log the exception instead of printing the stack trace
            logger.log(Level.SEVERE, "An error occurred while loading dashboard view", e);
        }

        return adminDashBoardView;
    }

    // Admin Manage Service Providers
    public AnchorPane getAdminManageServiceProviderView() {
        if (adminManageServiceProviderView == null) {
            try {
                adminManageServiceProviderView = new FXMLLoader(getClass().getResource("/Fxml/Admin/ManageServiceProvider.fxml")).load();
            } catch (Exception e) {
                // Log the exception instead of printing the stack trace
                logger.log(Level.SEVERE, "An error occurred while loading dashboard view", e);
            }
        }
        return adminManageServiceProviderView;
    }

    // Admin Booking Views
    public AnchorPane getAdminBookingsView() {
        // To maintain the Referential Integrity
        // When a service provider is deleted or updated it's content should be reflected in the Bookings Menu.
        try {
            adminManageBookingsView = new FXMLLoader(getClass().getResource("/Fxml/Admin/ManageBookings.fxml")).load();
        } catch (Exception e) {
            // Log the exception instead of printing the stack trace
            logger.log(Level.SEVERE, "An error occurred while loading dashboard view", e);
        }

        return adminManageBookingsView;
    }



    // Admin Read Suggestions View
    public AnchorPane getReadSuggestionsView() {
        // To maintain the Referential Integrity
        // When a service provider is deleted or updated it's content should be reflected in the DashBoard Menu.
        try {
            adminReadSuggestionsView = new FXMLLoader(getClass().getResource("/Fxml/Admin/ReadSuggestions.fxml")).load();
        } catch (Exception e) {
            // Log the exception instead of printing the stack trace
            logger.log(Level.SEVERE, "An error occurred while loading dashboard view", e);
        }

        return adminReadSuggestionsView;
    }



    // Suggestions Grid View
    public StackPane showReadSuggestionsGridView(Suggestion suggestion) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/ReadSuggestionsGridBox.fxml"));
            ReadSuggestionsGribBoxController readSuggestionsGribBoxController = new ReadSuggestionsGribBoxController();
            loader.setController(readSuggestionsGribBoxController);

            readSuggestionsGribBoxController.setSuggestion(suggestion);

            return loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void showAdminWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Admin.fxml"));

        AdminController admincontroller = new AdminController();
        loader.setController(admincontroller);

        createStage(loader);
    }



    // Common Views
    // Service Provider Profile View
    public void showServiceProvideProfileView(ServiceProvider serviceProvider) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/ServiceProviderProfile.fxml"));
        ServiceProviderProfileController serviceProviderProfileController = new ServiceProviderProfileController();
        loader.setController(serviceProviderProfileController);

        serviceProviderProfileController.setServiceProvider(serviceProvider);

        createStage(loader);
    }


    // Category Grid View
    public StackPane showCategoryGridView(String categoryName, String imagePath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/ServiceSeeker/CategoryGridBox.fxml"));
            CategoryGridBoxController categoryGridBoxController = new CategoryGridBoxController();
            loader.setController(categoryGridBoxController);

            categoryGridBoxController.setServiceType(categoryName);
            categoryGridBoxController.setImagePath(imagePath);

            return loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    // Service Providers Grid View
    public StackPane showServiceProviderGridView(ServiceProvider serviceProvider) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/ServiceSeeker/ServiceProviderGridBox.fxml"));
            ServiceProviderGridBoxController serviceProviderGridBoxController = new ServiceProviderGridBoxController();
            loader.setController(serviceProviderGridBoxController);

            serviceProviderGridBoxController.setServiceProvider(serviceProvider);

            return loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    // View Booking
    public void showBookingView(Booking booking) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/ViewBooking.fxml"));
        ViewBookingController viewBookingController = new ViewBookingController();
        loader.setController(viewBookingController);

        viewBookingController.setBooking(booking);

        createStage(loader);
    }


    // Give Rating Page
    public void showRatingView(Booking booking) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/ServiceSeeker/Rating.fxml"));
        RatingController ratingController = new RatingController(booking);
        loader.setController(ratingController);

        createStage(loader);
    }







    // Login Window
    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
        createStage(loader);
    }

    // SignUp Window
    public void showSingUpWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Signup.fxml"));
        createStage(loader);
    }

    // Creates the Stage
    private void createStage(FXMLLoader loader) {
        Scene scene = null;

        try {
            scene = new Scene(loader.load());
        } catch (Exception e) {
            // Log the exception instead of printing the stack trace
            logger.log(Level.SEVERE, "An error occurred while creating the stage", e);
        }

        Stage stage = new Stage();
        if (scene != null) {
            stage.setScene(scene);
        } else {
            // Log a warning if the scene is null
            logger.warning("Scene is null. Stage creation failed.");
            return;
        }

        //stage.initStyle(StageStyle.TRANSPARENT);

        stage.setTitle("Proximity Connect");
        // Set the icon of the stage
        File iconFile = new File("D:/IntellijProjects/LSD/src/main/resources/Images/ProximityConnect.png");
        Image icon = new Image(iconFile.toURI().toString());
        stage.getIcons().add(icon);

        stage.setResizable(false);
        stage.show();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }

}
