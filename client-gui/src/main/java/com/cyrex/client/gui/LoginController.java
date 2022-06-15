package com.cyrex.client.gui;

import com.cyrex.client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


public class LoginController {
    @FXML
    Text actionTarget;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button btn;

    @FXML
    protected void handleSubmitButtonAction() {
        actionTarget.setText("Signing in...");
        Client.setLogin(usernameField.getText());
        Client.setPassword(passwordField.getText());
        Client.setCommand("login");
        Client.sendRequest();
        actionTarget.setText(Client.getResult());
    }
}
