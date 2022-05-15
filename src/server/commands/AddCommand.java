package server.commands;

import route.Route;
import server.Server;
import server.data.Data;

/**
 * Команда для добавления нового элемента в коллекцию через комндную строку
 */

public class AddCommand extends Command {
    public AddCommand() {
        super("add", "adds an element to the collection of routes");
    }

    public String execute() {
        try {
            Route newRoute = Data.generateAndSetId((Route) Server.argument);
            Data.getRoutes().add(newRoute);
            Data.getCommands().get("sort_by_distance").execute();
            return "new element added to the collection";
        } catch (ClassCastException ex) {
            return "invalid argument";
        }
    }
}
