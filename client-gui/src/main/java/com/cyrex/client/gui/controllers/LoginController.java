package com.cyrex.client.gui.controllers;

import com.cyrex.client.Client;
import com.cyrex.client.gui.views.RegisterView;
import javafx.application.Application;
import javafx.fxml.FXML;
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
    protected void handleLoginButtonAction() {
        username = usernameField.getText().trim();
        password = passwordField.getText().trim();
        actionTarget.setText("Logging in...");
        actionTarget.setText(Client.login(username, password));
    }

    @FXML
    protected void handleRegisterButtonAction(){
        ViewController.switchToRegisterView();
    }
}
