package com.cyrex.client.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class HelpController {
    @FXML
    private Button exitBtn;
    @FXML
    private TextArea helpArea;
    public void exit(ActionEvent actionEvent) {
        ((Stage) exitBtn.getScene().getWindow()).close();
    }

    public void displayHelp(String text) {
        helpArea.setText(text);
    }
}
