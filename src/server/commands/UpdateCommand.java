package server.commands;

import route.Route;
import server.Server;
import server.data.Data;

public class UpdateCommand extends Command {

    public UpdateCommand() {
        super("update", "updates the route with required id");
    }

    boolean found = false;

    @Override
    public String execute() {
        try {
            Route updatedRoute = (Route) Server.argument;
            int idForUpdating = updatedRoute.getId();
            if (!Data.getRoutes().isEmpty()) {
                if (Data.getRoutes().removeIf(route -> route.getId() == idForUpdating)) {
                    Data.getRoutes().add(updatedRoute);
                    return "route with id = " + idForUpdating + " updated";
                }
                return "route with required id not found";
            }

            return "collection is empty";
        } catch (ClassCastException cce) {
            return "invalid argument";
        }
    }
}
