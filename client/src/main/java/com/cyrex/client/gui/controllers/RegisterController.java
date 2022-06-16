package com.cyrex.client.gui.controllers;

import com.cyrex.client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    private Button registerBtn;
    @FXML
    private Button backBtn;

    @FXML
    private void handleRegisterButtonAction() {
        username = usernameField.getText().trim();
        password = passwordField.getText().trim();
        repeatedPassword = repeatPasswordField.getText().trim();
        if (!password.equals(repeatedPassword)) {
            actionTarget.setText("Passwords don't match, try again.");
            return;
        }
        String result = Client.register(username, password);
        actionTarget.setText("Registering...");
        if (result.equals("registration successful")) ViewController.switchToMainView(registerBtn);
        actionTarget.setText(result);
    }

    public void handleBackButtonAction(ActionEvent actionEvent) {
        ViewController.switchToLoginView(backBtn);
    }
}
