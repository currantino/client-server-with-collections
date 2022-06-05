package server.commands;

import server.jdbcServer;
import server.data.Data;

public class RemoveByIdCommand extends Command {

    public RemoveByIdCommand() {
        super("remove_by_id", "removes the element with the given id");
    }

    @Override
    public String execute() {
        try {
            int id = Integer.parseInt((String) jdbcServer.argument);
            if (!Data.getRoutes().isEmpty()) {
                if (Data.getRoutes().removeIf(route -> route.getId() == id)) {
                    return "mid.route with id = " + id + " removed";
                } else {
                    return "mid.route not found";
                }
            }
            return "collection is empty";
        } catch (NumberFormatException e) {
            return "invalid id";
        }
    }
}
