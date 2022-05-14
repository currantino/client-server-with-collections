package server.commands;

import route.Route;
import server.data.Data;

public class ShowCommand extends Command {
    public ShowCommand() {
        this.name = "show";
        this.desc = "shows contents of the collection of routes";
    }

    public String execute() {
        if (!Data.getRoutes().isEmpty()) {
            for (Route route : Data.getRoutes()) {
                result = result.concat(route.toString());
            }
        } else {
            result = "collection is empty";
        }
        return result;
    }
}