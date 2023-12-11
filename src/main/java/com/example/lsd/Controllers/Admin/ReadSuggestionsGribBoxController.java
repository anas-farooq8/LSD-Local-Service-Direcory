package com.example.lsd.Controllers.Admin;

import com.example.lsd.Models.Suggestion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ReadSuggestionsGribBoxController implements Initializable {
    private Suggestion suggestion;

    @FXML
    private Label number;

    @FXML
    private TextArea seeker_comment;

    @FXML
    private TextField comment_date;

    @FXML
    private Label seeker_username;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        number.setText(String.valueOf(suggestion.getSuggestionId()));
        seeker_username.setText(suggestion.getSeekerUsername());
        seeker_comment.setText(suggestion.getComment());
        comment_date.setText(suggestion.getCommentDate().toString());

    }

    public void setSuggestion(Suggestion suggestion) {
        this.suggestion = suggestion;
    }

}
