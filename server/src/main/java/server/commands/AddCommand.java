package server.commands;

import common.ServerRequest;
import common.route.Route;
import server.commands.types.Writable;
import server.data.Data;

import static server.Server.pdb;

/**
 * Команда для добавления нового элемента в коллекцию через командную строку
 */

public class AddCommand extends ArgumentableCommand implements Writable {

    public AddCommand() {
        super("add", "adds an element to the collection of routes");
    }

    public String execute(ServerRequest request) {
        unpackRequest(request);
        setArgument(request);
        if (argument != null) {
            Route newRoute = (Route) argument;
            int newRouteId = pdb.addElement(newRoute, login, password);
            if (newRouteId != -1) {
                newRoute.setId(newRouteId);
                Data.getRoutes().add(newRoute);
                return "new route added successfully";
            }
            return "route adding failed :(";
        }
        return "route is null";
    }
}
