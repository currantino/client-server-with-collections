package com.cyrex.client.gui.controllers;

import com.cyrex.client.Client;
import common.route.Route;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private TableView<Route> routesTable;
    @FXML
    private TableColumn<Route, Integer> routeId;
    @FXML
    private TableColumn<Route, String> routeName;
    @FXML
    private TableColumn<Route, Double> routeDistance;
    @FXML
    private TableColumn<Route, LocalDateTime> routeCreationDate;
    @FXML
    private Button removeBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        routeId.setCellValueFactory(new PropertyValueFactory<Route, Integer>("id"));
        routeName.setCellValueFactory(new PropertyValueFactory<Route, String>("name"));
        routeDistance.setCellValueFactory(new PropertyValueFactory<Route, Double>("distance"));
        routeCreationDate.setCellValueFactory(new PropertyValueFactory<Route, LocalDateTime>("creationDate"));
        routesTable.getItems().setAll(getRoutesFromServer());
    }

    private List<Route> getRoutesFromServer() {
        Client.setCommand("show");
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
            routesFromServer.add(newRoute);
        }
        return routesFromServer;
    }

    public void handleRemoveButtonAction(ActionEvent actionEvent) {
        int idToRemove = routesTable.getSelectionModel().getSelectedItems().get(0).getId();
        Client.setCommand("remove_by_id");
        Client.setArgument(idToRemove);
        Client.sendRequest();
        String result = Client.getResult();
        removeBtn.setText(result);
        if (result.equals("route with id = " + idToRemove + " has been removed"))
            routesTable.getItems().removeAll(routesTable.getSelectionModel().getSelectedItems());
    }
}
