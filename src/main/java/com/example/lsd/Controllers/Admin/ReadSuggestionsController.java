package com.example.lsd.Controllers.Admin;

import com.example.lsd.DBUtils.DBUtils;
import com.example.lsd.Models.Suggestion;
import com.example.lsd.Views.ViewFactory;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ReadSuggestionsController implements Initializable {

    @FXML
    private GridPane suggestionGridPane;

    private ObservableList<Suggestion> allSuggestions;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.allSuggestions = DBUtils.getAllSuggestions();
        onSuggestionGridBoxDisplay();
    }

    private void onSuggestionGridBoxDisplay() {
        // n rows each have 1 column

        int row = 0;
        int column = 0;

        try {
            suggestionGridPane.getColumnConstraints().clear();
            suggestionGridPane.getRowConstraints().clear();
            suggestionGridPane.getChildren().clear();

            for (Suggestion suggestion : allSuggestions) {

                if (column == 1) {
                    column = 0;
                    row++;
                }

                StackPane pane = ViewFactory.getInstance().showReadSuggestionsGridView(suggestion);

                suggestionGridPane.add(pane, column++, row);
                suggestionGridPane.setMargin(pane, new Insets(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
