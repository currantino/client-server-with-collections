package server.commands;

import server.Server;
import server.data.Data;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class RemoveByIdCommand extends Command {

    public RemoveByIdCommand() {
        name = "remove_by_id";
        desc = "removes required route from the collection";
    }

    @Override
    public String execute() {
        if (!Data.getRoutes().isEmpty()) {
            int id = Integer.parseInt(Server.argument);
            Data.setRoutes(Data.getRoutes()
                    .stream()
                    .dropWhile(route -> route.getId() == id)
                    .collect(Collectors.toCollection(LinkedList::new)));
            return "element was removed";
        }
        return "collection is empty";
    }
}