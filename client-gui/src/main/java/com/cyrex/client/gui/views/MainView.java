package com.cyrex.client.gui.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainView extends Application{
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginView.class.getResource("/com.cyrex.client.gui/register.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        stage.setTitle("Routes manager");
        stage.setScene(scene);
        stage.show();
    }
}
