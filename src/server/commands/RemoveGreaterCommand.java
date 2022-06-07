package server.commands;

import mid.route.Route;
import server.JdbcServer;
import server.Writable;
import server.data.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static server.JdbcServer.*;

public class RemoveGreaterCommand extends Command implements Writable {

    public RemoveGreaterCommand() {
        super("remove_greater", "removes elements greater than required");
    }

    @Override
    public String execute() {
        try {
            double dist = Double.parseDouble((String) JdbcServer.argument);
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
