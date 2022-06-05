package server.commands;

import route.Route;
import server.data.Data;
import server.jdbcServer;

import static server.data.Data.pdb;

/**
 * Команда для добавления нового элемента в коллекцию через командную строку
 */

public class AddCommand extends SqlCommand {

    public AddCommand() {
        super("add", "adds an element to the collection of routes");
    }

    public String execute() {
        if (jdbcServer.argument != null) {
            Route newRoute = (Route) jdbcServer.argument;
            if (pdb.add(newRoute)) {
                Data.getRoutes().add(newRoute);
                Data.getCommands().get("sort_by_distance").execute();
                return "new route added successfully";
            }
        } else return "route is null";
        return ":(";
    }
}
