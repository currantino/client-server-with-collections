package server.commands;

import mid.route.Route;
import server.data.Data;
import server.jdbcServer;

import static server.jdbcServer.pdb;

/**
 * Команда для добавления нового элемента в коллекцию через командную строку
 */

public class AddCommand extends Command {

    public AddCommand() {
        super("add", "adds an element to the collection of routes");
    }

    public String execute() {
        if (jdbcServer.argument != null) {
            Route newRoute = (Route) jdbcServer.argument;
            if (pdb.addElement(newRoute)) {
                Data.getRoutes().add(newRoute);
                Data.getCommands().get("sort_by_distance").execute();
                return "new mid.route added successfully";
            }
        } else return "mid.route is null";
        return ":(";
    }
}
