package server.commands;

import mid.ServerRequest;
import mid.route.Route;
import server.commands.types.Writable;
import server.data.Data;

import static server.NetworkManager.pdb;

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
            if (pdb.addElement(newRoute, login, password)) {
                Data.setRoutes(pdb.getElements());
                return "new route added successfully";
            }
        } else return "route is null";
        return ":(";
    }
}
