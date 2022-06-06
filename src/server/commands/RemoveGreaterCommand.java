package server.commands;

import server.data.Data;
import server.jdbcServer;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class RemoveGreaterCommand extends Command {

    public RemoveGreaterCommand() {
        super("remove_greater", "removes elements greater than required");
    }

    @Override
    public String execute() {
        try {
            double dist = Double.parseDouble((String) jdbcServer.argument);
            if (!Data.getRoutes().isEmpty()) {
                Data.setRoutes(Data.getRoutes().stream().filter(route -> route.getDistance() <= dist).collect(Collectors.toCollection(LinkedList::new)));
                return "routes with distance greater than " + dist + " have been removed";
            }
            return "collections is empty";
        } catch (NumberFormatException e) {
            return "invalid argument";
        }
    }
}
