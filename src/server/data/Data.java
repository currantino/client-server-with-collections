package server.data;

import org.json.JSONArray;
import route.Route;
import server.commands.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
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

    private static String autoSavePath = "resources/autoSave.json";

    private static TreeMap<String, Command> commands = new TreeMap();
    private static LinkedList<Route> routes = new LinkedList();
    public static boolean isSaved = false;

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
        commands.put("remove_by_id", new RemoveByIdCommand());
        commands.put("update", new UpdateCommand());
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

    public static LinkedList<Route> getRoutes() {
        return routes;
    }

    public static void setRoutes(LinkedList<Route> routes) {
        Data.routes = routes;
    }
}
