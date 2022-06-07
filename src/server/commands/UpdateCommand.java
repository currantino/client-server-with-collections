package server.commands;

import mid.route.Route;
import server.Writable;
import server.data.Data;

import static server.JdbcServer.*;

public class UpdateCommand extends Command implements Writable {

    public UpdateCommand() {
        super("update", "updates the route with required id");
    }

    @Override
    public String execute() {
        if (argument != null) {
            Route routeToUpdate = (Route) argument;
            if (pdb.checkExistence(routeToUpdate.getId())) {
                if (pdb.checkCreator(routeToUpdate.getId(), login, password)) {
                    if (pdb.updateElement(routeToUpdate)) {
                        Data.getRoutes().removeIf(route -> route.getId() == routeToUpdate.getId());
                        Data.getRoutes().add(routeToUpdate);
                        return "route with id = " + routeToUpdate.getId() + " updated successfully";
                    }else return "updating failed";
                } else return "you cannot change elements created by other users";
            }
            else return "route with id = " + routeToUpdate.getId() + " not found";
        }
        else return "route provided for updating is null";
    }
}
