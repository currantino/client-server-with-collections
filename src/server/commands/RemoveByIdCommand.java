package server.commands;

import server.Server;
import server.data.Data;

public class RemoveByIdCommand extends Command {

    public RemoveByIdCommand() {
        super("remove_by_id", "removes the element with the given id");
    }

    @Override
    public String execute() {
        try {
            int id = Integer.parseInt((String) Server.argument);
            if (!Data.getRoutes().isEmpty()) {
                if (Data.getRoutes().removeIf(route -> route.getId() == id)) {
                    return "route with id = " + id + " removed";
                } else {
                    return "route not found";
                }
            }
            return "collection is empty";
        } catch (NumberFormatException e) {
            return "invalid id";
        }
    }
}
