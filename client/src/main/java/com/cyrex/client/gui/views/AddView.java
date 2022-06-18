package com.cyrex.client.gui.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AddView extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(AddView.class.getResource("/com.cyrex.client.gui/add.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add(AddView.class.getResource("/com.cyrex.client.gui/styles/add.css").toExternalForm());
        stage.setTitle("add");
        stage.setScene(scene);
        stage.show();
    }
}
