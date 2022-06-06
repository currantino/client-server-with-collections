package server.commands;

import mid.route.Route;
import server.data.Data;

import static server.jdbcServer.argument;
import static server.jdbcServer.pdb;

public class UpdateCommand extends Command {

    public UpdateCommand() {
        super("update", "updates the route with required id");
    }

    @Override
    public String execute() {
        if (argument != null) {
            Route routeToUpdate = (Route) argument;
            if (pdb.updateElement(routeToUpdate)) {
                Data.getRoutes().add(routeToUpdate);
                return "route with id = " + routeToUpdate.getId() + " updated successfully";
            } else
                return "route with id = " + routeToUpdate.getId() + " not found";

        } else return "route provided for updating is null";
    }
}
