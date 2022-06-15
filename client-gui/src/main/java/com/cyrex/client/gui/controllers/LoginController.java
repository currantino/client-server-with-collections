package com.cyrex.client.gui.controllers;

import com.cyrex.client.Client;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


public class LoginController {
    @FXML
    Text actionTarget;
    private String username;
    private String password;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginBtn;
    @FXML
    private Button registerBtn;

    @FXML
    protected void handleLoginButtonAction() {
        username = usernameField.getText().trim();
        password = passwordField.getText().trim();
        actionTarget.setText("Logging in...");
        actionTarget.setText(Client.login(username, password));
    }

    @FXML
    protected void handleRegisterButtonAction() {
        ViewController.switchToRegisterView(registerBtn);
    }

    @FXML
    public void handleExitButtonAction() {
        Platform.exit();
        System.exit(0);
    }
}
