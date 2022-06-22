package com.cyrex.client.gui.controllers;

import com.cyrex.client.Client;
import common.route.Route;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDateTime;

public class UpdateController {
    @FXML
    public TextField nameTextField;
    @FXML
    public TextField distanceTextField;
    @FXML
    public TextField departureNameTextField;
    @FXML
    public TextField departureXTextField;
    @FXML
    public TextField departureYTextField;
    @FXML
    public TextField departureZTextField;
    @FXML
    public TextField destinationNameTextField;
    @FXML
    public TextField destinationXTextField;
    @FXML
    public TextField destinationYTextField;
    @FXML
    public TextField destinationZTextField;
    @FXML
    TextField idTextField;
    @FXML
    private Button confirmBtn;

    @FXML
    public void confirm() {
        int idToUpdate = Integer.parseInt(idTextField.getText().trim());
        String name = nameTextField.getText();
        Float distance = Float.parseFloat(distanceTextField.getText().trim());
        String departureName = departureNameTextField.getText().trim();
        int departureX = Integer.parseInt(departureXTextField.getText().trim());
        int departureY = Integer.parseInt(departureYTextField.getText().trim());
        int departureZ = Integer.parseInt(departureZTextField.getText().trim());
        String destinationName = destinationNameTextField.getText().trim();
        int destinationX = Integer.parseInt(destinationXTextField.getText().trim());
        int destinationY = Integer.parseInt(destinationYTextField.getText().trim());
        int destinationZ = Integer.parseInt(destinationZTextField.getText().trim());
        LocalDateTime creationDate = LocalDateTime.now().withNano(0);
        Route newRoute = new Route(idToUpdate, name, distance, departureName, departureX, departureY, departureZ, destinationName, destinationX, destinationY, destinationZ, creationDate);
        Client.setCommand("update");
        Client.setArgument(newRoute);
        Client.sendRequest();
        ((Stage) confirmBtn.getScene().getWindow()).close();
    }
}
