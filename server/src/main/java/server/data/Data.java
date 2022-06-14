package server.data;

import common.route.Route;
import server.commands.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Класс для хранения коллекции и всех команд
 */

public class Data {

    private static final Map<String, Command> commands = new TreeMap<>();
    private static List<Route> routes = new LinkedList<>();

    public static Map<String, Command> getCommands() {
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
        commands.put("register", new RegisterCommand());
        commands.put("suicide", new RemoveUserCommand());
        commands.put("login", new LoginCommand());
    }

    public static List<Route> getRoutes() {
        return routes;
    }

    public static void setRoutes(List<Route> routes) {
        Data.routes = routes;
    }
}
