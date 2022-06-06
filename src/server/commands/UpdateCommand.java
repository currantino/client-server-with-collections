package server.commands;

import mid.route.Route;
import server.data.Data;

import static server.jdbcServer.*;

public class UpdateCommand extends Command {

    public UpdateCommand() {
        super("update", "updates the route with required id");
    }

    @Override
    public String execute() {
        if (argument != null) {
            Route routeToUpdate = (Route) argument;
            if (pdb.checkCreator(routeToUpdate.getId(), login, password)) {
                System.out.println("userId is " + routeToUpdate.getId());
                System.out.println("creatorId");
                if (pdb.updateElement(routeToUpdate, login, password)) {
                    Data.getRoutes().add(routeToUpdate);
                    return "route with id = " + routeToUpdate.getId() + " updated successfully";
                } else return "route with id = " + routeToUpdate.getId() + " not found";
            } else return "you cannot change elements created by other users";
        }
        return "route provided for updating is null";
    }
}
