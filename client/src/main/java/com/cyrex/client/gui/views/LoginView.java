package com.cyrex.client.gui.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class LoginView extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginView.class.getResource("/com.cyrex.client.gui/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        scene.getStylesheets().add(LoginView.class.getResource("/com.cyrex.client.gui/styles/application.css").toExternalForm());
        stage.setTitle("login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
