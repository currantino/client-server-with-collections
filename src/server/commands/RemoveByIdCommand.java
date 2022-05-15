package server.commands;

import server.Server;
import server.data.Data;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class RemoveByIdCommand extends Command {

    public RemoveByIdCommand() {
        super("remove_by_id", "removes the element with the given id");
    }

    @Override
    public String execute() {
        int id = Integer.parseInt(Server.argument);
        if (!Data.getRoutes().isEmpty()) {
            if (Data.getRoutes().stream().anyMatch(route -> route.getId() == id)) {
                Data.setRoutes(Data.getRoutes().stream()
                        .filter(route -> route.getId() != id)
                        .collect(Collectors.toCollection(LinkedList::new)));
                return "route with id = " + id + " removed";
            } else {
                return "route not found";
            }
        }
        return "collection is empty";
    }
}
