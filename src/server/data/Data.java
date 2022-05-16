package server.data;

import org.json.JSONArray;
import org.json.JSONObject;
import route.Route;
import server.commands.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeMap;

/**
 * Класс для хранения коллекции и всех команд
 */

public class Data {

    private static int idForNewRoutes;

    public static String getAutoSavePath() {
        return autoSavePath;
    }

    public static void setAutoSavePath(String autoSavePath) {
        Data.autoSavePath = autoSavePath;
    }

    private static String autoSavePath = "/Users/boi/Desktop/client-server-with-collections/resources/autoSave.json";

    private static TreeMap<String, Command> commands = new TreeMap();
    private static LinkedList<Route> routes = new LinkedList();
    public static HashSet<String> executedScripts = new HashSet<>();
    public static boolean isSaved = false;

//    public static HashSet<String> getExecutedScripts() {
//        return executedScripts;
//    }

    public static TreeMap<String, Command> getCommands() {
        return commands;
    }

    public static void setCommands() {
        commands.put("exit", new ExitCommand());
        commands.put("help", new HelpCommand());
        commands.put("show", new ShowCommand());
        commands.put("add", new AddCommand());
        commands.put("average_of_distance", new AvgDistanceCommand());
        commands.put("info", new InfoCommand());
        commands.put("clear", new ClearCommand());
        commands.put("sort_by_distance", new SortByDistanceCommand());
        commands.put("min_by_creation_date", new MinByCreationDateCommand());
        commands.put("print_unique_distance", new PrintUniqueDistanceCommand());
//        commands.put("execute_script", new ExecuteScriptCommand());
        commands.put("remove_by_id", new RemoveByIdCommand());
        commands.put("update", new UpdateCommand());
//        commands.put("read", new JsonReadCommand());
//        commands.put("save", new SaveCommand());
        commands.put("sort_by_creation_date", new SortByDateTimeCommand());
        commands.put("head", new HeadCommand());
        commands.put("remove_greater", new RemoveGreaterCommand());
        commands.put("remove_lower", new RemoveLowerCommand());
        commands.put("load_auto_save", new LoadAutoSaveCommand());
    }

    public static Route generateAndSetId(Route route) {
        route.setId(idForNewRoutes);
        idForNewRoutes++;
        return route;
    }

    public static boolean autoSave() {
        if (!getRoutes().isEmpty()) {
            JSONArray jsonArray = new JSONArray(getRoutes());
            try {
                PrintWriter printWriter = new PrintWriter(autoSavePath);
                printWriter.write(jsonArray.toString());
                printWriter.close();
                Data.isSaved = true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return isSaved;
    }

    public static boolean readAutoSave() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        try {
            String myJson = Files.readString(Path.of(autoSavePath));
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
                Data.routes.add(newRoute);
                new SortByDistanceCommand().execute();
                Data.isSaved = true;
            }
            System.out.println("routes from the input file were added to the collection");
            return true;
        } catch (IOException e) {
            System.out.println("autoSave file does not exist");
        }
        return false;
    }


    public static LinkedList<Route> getRoutes() {
        return routes;
    }

    public static void setRoutes(LinkedList<Route> routes) {
        Data.routes = routes;
    }


}
