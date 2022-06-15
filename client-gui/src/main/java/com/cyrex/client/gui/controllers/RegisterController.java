package com.cyrex.client.gui.controllers;

import com.cyrex.client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class RegisterController {
    private String password;
    private String username;
    private String repeatedPassword;
    @FXML
    private Text actionTarget;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField repeatPasswordField;

    @FXML
    private void handleRegisterButtonAction() {
        username = usernameField.getText().trim();
        password = passwordField.getText().trim();
        repeatedPassword = repeatPasswordField.getText().trim();
        if (!password.equals(repeatedPassword)) {
            actionTarget.setText("Passwords don't match, try again.");
            return;
        }
        actionTarget.setText("Registering...");
        actionTarget.setText(Client.register(username, password));
    }
}
