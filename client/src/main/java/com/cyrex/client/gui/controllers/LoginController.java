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
    private Text actionTarget;
    @FXML
    private String username;
    @FXML
    private String password;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginBtn;
    @FXML
    private Button signUpBtn;

    @FXML
    protected void login() {
        username = usernameField.getText().trim();
        password = passwordField.getText().trim();
        actionTarget.setText("Logging in...");
        String result = Client.login(username, password);
        if (result.equals("you are welcome!")) ViewController.switchToMainView(loginBtn);
        actionTarget.setText(result);
    }

    @FXML
    protected void signUp() {
        ViewController.switchToSignUpView(signUpBtn);
    }

    @FXML
    public void exit() {
        Platform.exit();
        System.exit(0);
    }
}
