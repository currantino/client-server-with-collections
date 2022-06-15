package com.cyrex.client.gui.controllers;

import com.cyrex.client.gui.views.LoginView;
import com.cyrex.client.gui.views.RegisterView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewController{
    public static void switchToRegisterView() {
        try {
            Parent root = FXMLLoader.load(RegisterView.class.getResource("/com.cyrex.client.gui/register.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root, 600, 400);
            stage.setTitle("Register");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
}
