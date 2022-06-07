package server.commands;

import mid.route.Route;
import server.JdbcServer;
import server.commands.types.Writable;
import server.data.Data;

import static server.JdbcServer.pdb;

/**
 * Команда для добавления нового элемента в коллекцию через командную строку
 */

public class AddCommand extends Command implements Writable {

    public AddCommand() {
        super("add", "adds an element to the collection of routes");
    }

    public String execute() {
        if (JdbcServer.argument != null) {
            Route newRoute = (Route) JdbcServer.argument;
            if (pdb.addElement(newRoute)) {
                Data.setRoutes(pdb.getElements());
                Data.getCommands().get("sort_by_distance").execute();
                return "new route added successfully";
            }
        } else return "route is null";
        return ":(";
    }
}
