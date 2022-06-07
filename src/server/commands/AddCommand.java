package server.commands;

import mid.ServerRequest;
import mid.route.Route;
import server.JdbcServer;
import server.commands.types.Argumentable;
import server.commands.types.Writable;
import server.data.Data;

import static server.JdbcServer.pdb;

/**
 * Команда для добавления нового элемента в коллекцию через командную строку
 */

public class AddCommand extends Command implements Writable, Argumentable<ServerRequest> {

    public AddCommand() {
        super("add", "adds an element to the collection of routes");
    }

    public String execute(ServerRequest request) {
        unpackRequest(request);
        if (argument != null) {
            Route newRoute = (Route) argument;
            if (pdb.addElement(newRoute)) {
                Data.setRoutes(pdb.getElements());
                Data.getCommands().get("sort_by_distance").execute();
                return "new route added successfully";
            }
        } else return "route is null";
        return ":(";
    }
}
