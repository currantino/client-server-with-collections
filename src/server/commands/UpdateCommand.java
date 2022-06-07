package server.commands;

import mid.ServerRequest;
import mid.route.Route;
import server.commands.types.Argumentable;
import server.commands.types.Writable;
import server.data.Data;

import static server.NetworkManager.pdb;

public class UpdateCommand extends Command implements Writable, Argumentable<ServerRequest> {

    public UpdateCommand() {
        super("update", "updates the route with required id");
    }

    @Override
    public String execute(ServerRequest request) {
        unpackRequest(request);
        if (argument != null) {
            Route routeToUpdate = (Route) argument;
            if (pdb.checkExistence(routeToUpdate.getId())) {
                if (pdb.checkCreator(routeToUpdate.getId(), login, password)) {
                    if (pdb.updateElement(routeToUpdate)) {
                        Data.getRoutes().removeIf(route -> route.getId() == routeToUpdate.getId());
                        Data.getRoutes().add(routeToUpdate);
                        return "route with id = " + routeToUpdate.getId() + " updated successfully";
                    } else return "updating failed";
                } else return "you cannot change elements created by other users";
            } else return "route with id = " + routeToUpdate.getId() + " not found";
        } else return "route provided for updating is null";
    }
}
