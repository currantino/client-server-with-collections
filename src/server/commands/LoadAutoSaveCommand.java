package server.commands;

import org.json.JSONArray;
import org.json.JSONObject;
import mid.route.Route;
import server.data.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class LoadAutoSaveCommand extends Command {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public LoadAutoSaveCommand() {
        super("load_auto_save", "loads data autoSave file");
    }

    @Override
    public String execute() {

        LinkedList<Route> routesFromTheFile = new LinkedList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        try {
            String myJson = Files.readString(Path.of(Data.getAutoSavePath()));
            JSONArray jsonArray = new JSONArray(myJson);

            for (Object element : jsonArray) {
                JSONObject jo = (JSONObject) element;
                JSONObject coordinates = jo.getJSONObject("coordinates");
                JSONObject from = jo.getJSONObject("from");
                JSONObject to = jo.getJSONObject("to");
                String routeName = jo.getString("name");
                Float routeDistance = jo.getFloat("distance");
                int xCoordinate = coordinates.getInt("x");
                Float yCoordinate = coordinates.getFloat("y");
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
                Route newRoute = new Route(routeName, routeDistance, xCoordinate, yCoordinate, fromName, fromX, fromY, fromZ, toName, toX, toY, toZ, creationDate);
                routesFromTheFile.add(newRoute);
                new SortByDistanceCommand().execute();
                Data.isSaved = true;
            }
            Data.setRoutes(routesFromTheFile);
            return "routes from the input file were added to the collection";
        } catch (IOException e) {
            return "autoSave file does not exist";
        }
    }
}

