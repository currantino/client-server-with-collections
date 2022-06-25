package com.cyrex.client.gui.controllers;

import com.cyrex.client.Client;
import common.route.Route;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private ChoiceBox<String> languageChoiceBox;
    @FXML
    private ChoiceBox<String> countryChoiceBox;
    private void chooseLanguage(){
        String choice = languageChoiceBox.getValue();
        String country = countryChoiceBox.getValue();
        Locale locale = new Locale(choice, country);
    }
    @FXML
    private TextField removeLowerTextField;
    @FXML
    private Button removeGreaterBtn;
    @FXML
    private TextField removeGreaterTextField;
    @FXML
    private Button addBtn;
    @FXML
    private TableView<Route> routesTable;
    @FXML
    private TableColumn<Route, Integer> routeIdCln;
    @FXML
    private TableColumn<Route, String> routeNameCln;
    @FXML
    private TableColumn<Route, Double> routeDistanceCln;
    @FXML
    private TableColumn<Route, LocalDateTime> routeCreationDateCln;
    @FXML
    private TableColumn<Route, String> departureCln;
    @FXML
    private TableColumn<Route, String> destinationCln;
    @FXML
    private Button removeBtn;
    @FXML
    private Button helpBtn;
    @FXML
    private TextField resultField;
    @FXML
    private Button updateBtn;

    @FXML
    private Button refreshBtn;
    @FXML
    private List<Route> routesData;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        routesData = getRoutesFromServer();
        routeIdCln.setCellValueFactory(new PropertyValueFactory<Route, Integer>("id"));
        routeNameCln.setCellValueFactory(new PropertyValueFactory<Route, String>("name"));
        routeDistanceCln.setCellValueFactory(new PropertyValueFactory<Route, Double>("distance"));
        routeCreationDateCln.setCellValueFactory(new PropertyValueFactory<Route, LocalDateTime>("creationDate"));
        departureCln.setCellValueFactory(new PropertyValueFactory<Route, String>("fromName"));
        destinationCln.setCellValueFactory(new PropertyValueFactory<Route, String>("toName"));
        routesTable.getItems().setAll(routesData);
    }

    @FXML
    private List<Route> getRoutesFromServer() {
        Client.setCommand("refresh");
        Client.sendRequest();
        String routesJson = Client.getResult();
        LinkedList<Route> routesFromServer = new LinkedList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        JSONArray jsonArray = new JSONArray(routesJson);
        for (Object element : jsonArray) {
            JSONObject jo = (JSONObject) element;
            JSONObject from = jo.getJSONObject("from");
            JSONObject to = jo.getJSONObject("to");
            int id = jo.getInt("id");
            String routeName = jo.getString("name");
            Float routeDistance = jo.getFloat("distance");
            String fromName = from.getString("name");
            int fromX = from.getInt("x");
            int fromY = from.getInt("y");
            int fromZ = from.getInt("z");
            String toName = to.getString("name");
            int toX = to.getInt("x");
            int toY = to.getInt("y");
            int toZ = to.getInt("z");
            String strCreationDate = jo.getString("creationDate");
            LocalDateTime creationDate = LocalDateTime.parse(strCreationDate, formatter);
            Route newRoute = new Route(id, routeName, routeDistance, fromName, fromX, fromY, fromZ, toName, toX, toY, toZ, creationDate);
            System.out.println(newRoute.getFromName());
            System.out.println(newRoute.getToName());
            routesFromServer.add(newRoute);
        }
        return routesFromServer;
    }

    @FXML
    public void remove() {
        int idToRemove = routesTable.getSelectionModel().getSelectedItems().get(0).getId();
        Client.setCommand("remove_by_id");
        Client.setArgument(idToRemove);
        Client.sendRequest();
        String result = Client.getResult();
        resultField.setText(result);
        if (result.equals("route with id = " + idToRemove + " has been removed")) {
            routesTable.getItems().removeAll(routesTable.getSelectionModel().getSelectedItems());
            routesTable.getItems().setAll(getRoutesFromServer());
        }
    }

    @FXML
    public void refresh() {
        routesTable.getItems().clear();
        routesTable.getItems().setAll(getRoutesFromServer());
    }

    @FXML
    public void add() {
        ViewController.switchToAddView(addBtn);
        resultField.setText(Client.getResult());
        refresh();
    }

    @FXML
    public void clear() {
        Client.setCommand("clear");
        Client.sendRequest();
        resultField.setText(Client.getResult());
        refresh();
    }

    @FXML
    public void exit() {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    public void update() {
        ViewController.switchToUpdateView(updateBtn);
        resultField.setText(Client.getResult());
        refresh();
    }

    @FXML
    public void help() {
        ViewController.switchToHelpView(helpBtn);
    }

    @FXML
    public void removeGreater() {
        Client.setCommand("remove_greater");
        Client.setArgument(removeGreaterTextField.getText());
        Client.sendRequest();
        resultField.setText(Client.getResult());
        refresh();
    }

    @FXML
    public void removeLower() {
        Client.setCommand("remove_lower");
        Client.setArgument(removeLowerTextField.getText());
        Client.sendRequest();
        resultField.setText(Client.getResult());
        refresh();
    }
}