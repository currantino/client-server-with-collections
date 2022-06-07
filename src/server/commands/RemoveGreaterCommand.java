package server.commands;

import mid.ServerRequest;
import mid.route.Route;
import server.commands.types.Argumentable;
import server.commands.types.Writable;
import server.data.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static server.NetworkManager.pdb;

public class RemoveGreaterCommand extends ArgumentableCommand implements Writable {

    public RemoveGreaterCommand() {
        super("remove_greater", "removes elements greater than required");
    }

    @Override
    public String execute(ServerRequest request) {
        unpackRequest(request);
        try {
            double dist = Double.parseDouble((String) argument);
            if (!Data.getRoutes().isEmpty()) {
                List<Route> routesToDelete = Data.getRoutes().stream().filter(route -> (route.getDistance() > dist) && pdb.checkCreator(route.getId(), login, password)).collect(Collectors.toCollection(LinkedList::new));
                for (Route routeToDelete : routesToDelete) pdb.removeElementById(routeToDelete.getId());
                Data.setRoutes(pdb.getElements());
                return "routes with distance greater than " + dist + " have been removed";
            }
            return "collections is empty";
        } catch (NumberFormatException e) {
            return "invalid argument";
        }
    }
}
