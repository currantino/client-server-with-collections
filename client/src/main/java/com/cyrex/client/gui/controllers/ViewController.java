package com.cyrex.client.gui.controllers;

import com.cyrex.client.Client;
import com.cyrex.client.gui.HelpController;
import com.cyrex.client.gui.views.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewController {
    public static void switchToRegisterView(Node node) {
        ((Stage) node.getScene().getWindow()).close();
        try {
            Parent root = FXMLLoader.load(RegisterView.class.getResource("/com.cyrex.client.gui/register.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root, 600, 400);
            scene.getStylesheets().add(HelpView.class.getResource("/com.cyrex.client.gui/styles/login.css").toExternalForm());
            stage.setTitle("Register");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void switchToLoginView(Node node) {
        ((Stage) node.getScene().getWindow()).close();
        try {
            Parent root = FXMLLoader.load(LoginView.class.getResource("/com.cyrex.client.gui/login.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root, 600, 400);
            scene.getStylesheets().add(HelpView.class.getResource("/com.cyrex.client.gui/styles/login.css").toExternalForm());
            stage.setTitle("login");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void switchToMainView(Node node) {
        ((Stage) node.getScene().getWindow()).close();
        try {
            Parent root = FXMLLoader.load(MainView.class.getResource("/com.cyrex.client.gui/main.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root, 1200, 900);
            scene.getStylesheets().add(HelpView.class.getResource("/com.cyrex.client.gui/styles/add.css").toExternalForm());
            stage.setTitle("routes manager");
            stage.setScene(scene);
            stage.setResizable(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void switchToAddView(Node node) {
        try {
            Parent root = FXMLLoader.load(HelpView.class.getResource("/com.cyrex.client.gui/add.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root, 600, 400);
            scene.getStylesheets().add(HelpView.class.getResource("/com.cyrex.client.gui/styles/add.css").toExternalForm());
            stage.setTitle("add");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void switchToUpdateView(Node node){
        try {
            Parent root = FXMLLoader.load(UpdateView.class.getResource("/com.cyrex.client.gui/update.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root, 600, 400);
            scene.getStylesheets().add(UpdateView.class.getResource("/com.cyrex.client.gui/styles/add.css").toExternalForm());
            stage.setTitle("update");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void switchToHelpView(Node node) {
        try {
            FXMLLoader loader = new FXMLLoader(HelpView.class.getResource("/com.cyrex.client.gui/help.fxml"));
            Parent root = loader.load();
            HelpController helpController = loader.getController();
            Client.setCommand("help");
            Client.sendRequest();
            helpController.displayHelp(Client.getResult());

            Stage stage = new Stage();
            Scene scene = new Scene(root, 600, 400);
//            scene.getStylesheets().add(HelpView.class.getResource("/com.cyrex.client.gui/styles/help.css").toExternalForm());
            stage.setTitle("help");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
