package com.example.lsd;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.lsd.Views.ViewFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    private static final Logger logger = Logger.getLogger(App.class.getName());

    @Override
    public void start(Stage stage) {
        try {
            ViewFactory.getInstance().showLoginWindow();
            //ViewFactory.getInstance().showAdminWindow();
            //ViewFactory.getInstance().showServiceSeekerWindow("aiden_walker");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during application start", e);
        }
    }
}


