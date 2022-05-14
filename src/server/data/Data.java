package server.data;

import route.Route;
import server.commands.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeMap;

/**
 * Класс для хранения коллекции и всех команд
 */

public class Data {

    private static TreeMap<String, Command> commands = new TreeMap();
    public static LinkedList<Route> routes = new LinkedList();
    public static HashSet<String> executedScripts = new HashSet<>();
    public static boolean saved = false;

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
        commands.put("sort", new SortByDistanceCommand());
        commands.put("min_by_creation_date", new MinByCreationDateCommand());
//        commands.put("print_unique_distance", new PrintUniqueDistanceCommand());
//        commands.put("execute_script", new ExecuteScriptCommand());
//        commands.put("remove_by_id", new RemoveByIDCommand());
//        commands.put("update", new UpdateCommand());
//        commands.put("read", new JsonReadCommand());
//        commands.put("save", new SaveCommand());
        commands.put("sort_by_creation_date", new SortByDateTimeCommand());
        commands.put("head", new HeadCommand());
//        commands.put("remove_greater", new RemoveGreaterCommand());
//        commands.put("remove_lower", new RemoveLowerCommand());
    }

    public static LinkedList<Route> getRoutes() {
        return routes;
    }
}
