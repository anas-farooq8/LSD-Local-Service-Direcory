package com.example.lsd.Controllers.ServiceSeeker;

import com.example.lsd.DBUtils.DBUtils;
import com.example.lsd.Validator.Validator;
import com.example.lsd.Views.ViewFactory;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class SuggestionsController implements Initializable {

    private String username;

    @FXML
    private Button cancel_btn;

    @FXML
    private Button saveSuggestion_btn;

    @FXML
    private TextArea comment;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cancel_btn.setOnAction(e -> onCancel());
        saveSuggestion_btn.setOnAction(e -> onSaveSuggestion());

    }

    private void onSaveSuggestion() {
        String commentContent = comment.getText();
        if(commentContent == null || commentContent.trim().isEmpty()) {
            Validator.showErrorAlert("No Input!", "Please Enter some comment.");
            return;
        }

        if(DBUtils.insertSuggestion(username, commentContent)) {
            Validator.showInformationAlert("Comment Submitted", "ThankYou for your Feedback.");
        } else {
            Validator.showErrorAlert("Submit Failed", "Adding Comment Failed.");
        }

        onCancel();
    }

    private void onCancel() {
        ViewFactory.getInstance().getServiceSeekerSelectedMenu().setValue("Cancel Suggestions");
        Stage stage = (Stage) cancel_btn.getScene().getWindow();
        ViewFactory.getInstance().closeStage(stage);
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
