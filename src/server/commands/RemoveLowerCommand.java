package server.commands;

import mid.route.Route;
import server.commands.types.Writable;
import server.data.Data;
import server.JdbcServer;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static server.JdbcServer.*;

public class RemoveLowerCommand extends Command implements Writable {

    public RemoveLowerCommand() {
        super("remove_lower", "removes routes with distance lower than required");
    }

    @Override
    public String execute() {
        try {
            double dist = Double.parseDouble((String) JdbcServer.argument);
            if (!Data.getRoutes().isEmpty()) {
                List<Route> routesToDelete = Data.getRoutes().stream().filter(route -> (route.getDistance() < dist) && pdb.checkCreator(route.getId(), login, password)).collect(Collectors.toCollection(LinkedList::new));
                for (Route routeToDelete : routesToDelete) pdb.removeElementById(routeToDelete.getId());
                Data.setRoutes(pdb.getElements());
                return "routes with distance lower than " + dist + " have been removed";
            }
            return "collections is empty";
        } catch (NumberFormatException e) {
            return "invalid argument";
        }
    }
}
